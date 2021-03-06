package com.proxiad.schultagebuch.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.validator.constraint.NoteConstraint;

public class NoteValidator implements ConstraintValidator<NoteConstraint, Note> {

	@Override
	public boolean isValid(Note note, ConstraintValidatorContext context) {
		return Objects.nonNull(note) && note.getSchulstunde().getKlasse().getSchulerSet().contains(note.getSchuler());
	}

}
