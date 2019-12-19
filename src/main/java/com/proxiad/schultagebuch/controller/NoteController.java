package com.proxiad.schultagebuch.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
import com.proxiad.schultagebuch.service.ViewModellService;
import com.proxiad.schultagebuch.util.NoteUtils;

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
	private ViewModellService viewModellService;

	@RequestMapping(value = "/note")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleNotenAnzeigen() {
		return super.ansicht("notenForm", "listNote", noteService.findeAlle());
	}

	@RequestMapping(value = "/meine-noten")
	@PreAuthorize("hasRole('SCHULER')")
	public ModelAndView schulerViewAnzeigen(final Principal principal, final Locale locale) {
		Schuler schuler = schulerService.findeDurchBenutzername(principal.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schulerViewModel", viewModellService.schulerZuKinderViewModell(schuler, locale));
		attributes.put("listNoten", viewModellService.getListeDerNoteViewModelleDurchSchuler(schuler, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/meine-kinder")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public ModelAndView elternteilKinderAnzeigen(final Principal principal, final Locale locale) {
		Elternteil elternteil = elternteilService.findeDurchBenutzername(principal.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("elternteil", elternteil);
		attributes.put("listKinder", viewModellService.getListeDerKinderViewModelleDurchElternteil(elternteil, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/meine-kinder/{kindId}/noten")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public RedirectView elternteilKindNotenAnzeigen(@PathVariable(value = "kindId") final Long kindId,
			final Principal principal, final Locale locale, final RedirectAttributes attributes) {
		Elternteil elternteil = elternteilService.findeDurchBenutzername(principal.getName());
		Schuler kind = schulerService.findeElternteilKind(kindId, elternteil);
		attributes.addFlashAttribute("showSchulerfolg", true);
		attributes.addFlashAttribute("kind", kind);
		attributes.addFlashAttribute("listNoten",
				viewModellService.getListeDerNoteViewModelleDurchSchuler(kind, locale));
		return super.umleiten("/meine-kinder");
	}

	@RequestMapping(value = "/meine-schulstunden")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView lehrerSchulstundenAnzeigen(final Principal principal) {
		Lehrer lehrer = lehrerService.findeDurchBenutzername(principal.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("lehrer", lehrer);
		attributes.put("listSchulstunden", schulstundeService.findeDurchLehrer(lehrer));
		return super.ansicht("lehrerSchulstundenForm", attributes);
	}

	@RequestMapping(value = "/meine-schulstunden/schulstunde/{schulstundeId}/schuler/{schulerId}")
	@PreAuthorize("hasRole('LEHRER')")
	public ModelAndView schulerNotenAnzeigen(@PathVariable(value = "schulstundeId") final Long schulstundeId,
			@PathVariable(value = "schulerId") final Long schulerId, final Principal principal, final Locale locale) {
		Lehrer lehrer = lehrerService.findeDurchBenutzername(principal.getName());
		Schulstunde schulstunde = schulstundeService.findeLehrerSchulstunde(schulstundeId, lehrer);
		Schuler schuler = schulerService.findeDurchSchulstunde(schulerId, schulstunde);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("schuler", schuler);
		attributes.put("schulstunde", schulstunde);
		attributes.put("listNoten",
				viewModellService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler, schulstunde, locale));
		return super.ansicht("notenForm", attributes);
	}

	@RequestMapping(value = "/schulstunde/{schulstundeId}/schuler/{schulerId}/note/add")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView neueNote(@RequestHeader final String referer,
			@PathVariable(value = "schulstundeId") final Long schulstundeId,
			@PathVariable(value = "schulerId") final Long schulerId, final RedirectAttributes attributes) {
		noteModalAttributes("add", NoteUtils.erstellenNeueNoteMitSchulerUndSchulstunde(schulerService.finden(schulerId),
				schulstundeService.finden(schulstundeId)), attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/note/edit/{noteId}")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView bestehendeNote(@RequestHeader final String referer,
			@PathVariable(value = "noteId") final Long id, final RedirectAttributes attributes) {
		noteModalAttributes("edit", noteService.finden(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/note/save")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView noteSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "note") @Valid Note note, RedirectAttributes attributes) {
		noteService.speichern(note);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/note/delete/{noteId}")
	@PreAuthorize("hasRole('LEHRER')")
	public RedirectView noteLoeschen(@RequestHeader final String referer, @PathVariable(value = "noteId") final Long id,
			final RedirectAttributes attributes) {
		noteService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void noteModalAttributes(final String modalType, final Note note, final RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("note", note);
	}

}