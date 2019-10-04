package com.proxiad.schultagebuch.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.NoteService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.service.SchulstundeService;
import com.proxiad.schultagebuch.util.PersonUtils;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class NoteController extends AbstraktController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private LehrerService lehrerService;

	@Autowired
	private SchulstundeService schulstundeService;

	@RequestMapping(value = "/meine-noten")
	@PreAuthorize("hasRole('SCHULER')")
	public ModelAndView personViewZeigen(final Principal principal, final Locale locale) {
		return super.ansicht("notenForm", getSchulerViewAttributes(principal, locale));
	}

	@RequestMapping(value = "/meine-kinder")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public ModelAndView elternteilKinderZeigen(final Principal principal, final Locale locale) {
		return super.ansicht("notenForm", getElternteilViewAttributes(principal, locale));
	}

	@RequestMapping(value = "/meine-kinder/noten/{id}")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public RedirectView elternteilKindNotenZeigen(@PathVariable(value = "id") final int id, final Principal principal,
			final Locale locale, RedirectAttributes attributes) {
		Elternteil elternteil = (Elternteil) PersonUtils.getPersonAusBenutzerName(principal.getName(),
				elternteilService, locale);
		Schuler kind = schulerService.findElternteilKind(id, elternteil, locale);
		attributes.addFlashAttribute("showSchulerfolg", true);
		attributes.addFlashAttribute("kind", kind);
		attributes.addFlashAttribute("listNoten", noteService.getListNoteViewModelBySchuler(kind, locale));
		return super.umleiten("/meine-kinder");
	}

	@RequestMapping(value = "/meine-schulstunden")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView lehrerSchulstundenZeigen(final Principal principal, final Locale locale) {
		Lehrer lehrer = (Lehrer) PersonUtils.getPersonAusBenutzerName(principal.getName(), lehrerService, locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("lehrer", lehrer);
		attributes.put("listSchulstunden", lehrer.getSchulstundeSet());
		return super.ansicht("lehrerSchulstundenForm", attributes);
	}

	@RequestMapping(value = "/meine-schulstunden/schulstunde/{schulstundeId}/schuler/{schulerId}")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView schulerNotenZeigen(@PathVariable(value = "schulstundeId") final int schulstundeId,
			@PathVariable(value = "schulerId") final int schulerId, final Locale locale) {
		Schuler schuler = schulerService.find(schulerId, locale);
		Schulstunde schulstunde = schulstundeService.find(schulstundeId, locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schuler", schuler);
		attributes.put("schulstunde", schulstunde);
		attributes.put("listNoten",
				noteService.getListNoteViewModelBySchulerUndSchulstunde(schuler, schulstunde, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/schulstunde/{schulstundeId}/schuler/{schulerId}/note/add")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView neueNote(@RequestHeader final String referer,
			@PathVariable(value = "schulerId") final int schulerId,
			@PathVariable(value = "schulstundeId") final int schulstundeId, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("note",
				getNewNote(schulerService.find(schulerId, locale), schulstundeService.find(schulstundeId, locale)));
		return super.umleiten(referer);
	}

	@PostMapping(value = "/note/save")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView noteSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "note") @Valid Note note, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		noteService.save(note);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private Map<String, Object> getSchulerViewAttributes(final Principal principal, final Locale locale) {
		Schuler schuler = (Schuler) PersonUtils.getPersonAusBenutzerName(principal.getName(), schulerService, locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schuler", schuler);
		attributes.put("listNoten", noteService.getListNoteViewModelBySchuler(schuler, locale));
		return attributes;
	}

	private Map<String, Object> getElternteilViewAttributes(final Principal principal, final Locale locale) {
		Elternteil elternteil = (Elternteil) PersonUtils.getPersonAusBenutzerName(principal.getName(),
				elternteilService, locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("elternteil", elternteil);
		attributes.put("listKinder", noteService.getKinderViewModel(elternteil, locale));
		return attributes;
	}

	private Note getNewNote(final Schuler schuler, final Schulstunde schulstunde) {
		Note note = new Note();
		note.setSchuler(schuler);
		note.setSchulstunde(schulstunde);
		return note;
	}

}