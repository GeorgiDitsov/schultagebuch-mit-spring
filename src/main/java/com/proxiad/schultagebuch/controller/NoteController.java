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
import com.proxiad.schultagebuch.service.ViewModelService;
import com.proxiad.schultagebuch.util.NoteUtils;
import com.proxiad.schultagebuch.util.PersonUtils;
import com.proxiad.schultagebuch.util.ValidierungUtils;

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

	@Autowired
	private ViewModelService viewModelService;

	@RequestMapping(value = "/meine-noten")
	@PreAuthorize("hasRole('SCHULER')")
	public ModelAndView schulerViewAnzeigen(final Principal principal, final Locale locale) {
		Schuler schuler = getSchuler(principal.getName(), locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schulerViewModel", viewModelService.schulerZuKinderViewModel(schuler, locale));
		attributes.put("listNoten", viewModelService.getListeDerNoteViewModelleDurchSchuler(schuler, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/meine-kinder")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public ModelAndView elternteilKinderAnzeigen(final Principal principal, final Locale locale) {
		Elternteil elternteil = getElternteil(principal.getName(), locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("elternteil", elternteil);
		attributes.put("listKinder", viewModelService.getListeDerKinderViewModelleDurchElternteil(elternteil, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/meine-kinder/noten/{id}")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public RedirectView elternteilKindNotenAnzeigen(@PathVariable(value = "id") final Long id,
			final Principal principal, final Locale locale, RedirectAttributes attributes) {
		Schuler kind = schulerService.elternteilKindFinde(id, getElternteil(principal.getName(), locale), locale);
		attributes.addFlashAttribute("showSchulerfolg", true);
		attributes.addFlashAttribute("kind", kind);
		attributes.addFlashAttribute("listNoten",
				viewModelService.getListeDerNoteViewModelleDurchSchuler(kind, locale));
		return super.umleiten("/meine-kinder");
	}

	@RequestMapping(value = "/meine-schulstunden")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView lehrerSchulstundenAnzeigen(final Principal principal, final Locale locale) {
		Lehrer lehrer = getLehrer(principal.getName(), locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("lehrer", lehrer);
		attributes.put("listSchulstunden", schulstundeService.findeDurchLehrer(lehrer));
		return super.ansicht("lehrerSchulstundenForm", attributes);
	}

	@RequestMapping(value = "/meine-schulstunden/schulstunde/{schulstundeId}/schuler/{schulerId}")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView schulerNotenAnzeigen(@PathVariable(value = "schulstundeId") final Long schulstundeId,
			@PathVariable(value = "schulerId") final Long schulerId, final Principal principal, final Locale locale) {
		Schulstunde schulstunde = schulstundeService.lehrerSchulstundeFinden(schulstundeId,
				getLehrer(principal.getName(), locale), locale);
		Schuler schuler = schulerService.findeDurchSchulstunde(schulerId, schulstunde, locale);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schuler", schuler);
		attributes.put("schulstunde", schulstunde);
		attributes.put("listNoten",
				viewModelService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler, schulstunde, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/schulstunde/{schulstundeId}/schuler/{schulerId}/note/add")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView neueNote(@RequestHeader final String referer,
			@PathVariable(value = "schulstundeId") final Long schulstundeId,
			@PathVariable(value = "schulerId") final Long schulerId, final Locale locale,
			RedirectAttributes attributes) {
		noteModalAttributes("add",
				NoteUtils.getNeueNoteFuerSchulerUndSchulstunde(schulerService.finden(schulerId, locale),
						schulstundeService.finden(schulstundeId, locale)),
				attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/note/edit/{noteId}")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView bestehendeNote(@RequestHeader final String referer,
			@PathVariable(value = "noteId") final Long id, final Locale locale, RedirectAttributes attributes) {
		noteModalAttributes("edit", noteService.finden(id, locale), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/note/save")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView noteSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "note") @Valid Note note, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		noteService.speichern(note);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/note/delete/{noteId}")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView noteLoeschen(@RequestHeader final String referer, @PathVariable(value = "noteId") final Long id,
			final Locale locale, RedirectAttributes attributes) {
		noteService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private Schuler getSchuler(final String benutzerName, final Locale locale) {
		return (Schuler) PersonUtils.getPersonAusBenutzerName(benutzerName, schulerService, locale);
	}

	private Elternteil getElternteil(final String bennutzerName, final Locale locale) {
		return (Elternteil) PersonUtils.getPersonAusBenutzerName(bennutzerName, elternteilService, locale);
	}

	private Lehrer getLehrer(final String benutzerName, final Locale locale) {
		return (Lehrer) PersonUtils.getPersonAusBenutzerName(benutzerName, lehrerService, locale);
	}

	private void noteModalAttributes(final String modalType, final Note note, RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("note", note);
	}

}