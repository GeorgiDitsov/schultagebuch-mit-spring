package com.proxiad.schultagebuch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.exception.FalschServiceException;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonUtilsIT {

	private static final String BENUTZERNAME = "SomeBenutzername";

	@Mock
	private SchulerService schulerService;

	@Mock
	private RolleService rolleService;

	@Mock
	private Schuler schuler;

	@Test
	public void getPersonDurchBenutzerNameTest() {
		// Given
		schuler = new Schuler();

		// When
		when(schulerService.findeDurchBenutzerName(BENUTZERNAME)).thenReturn(schuler);
		Object person = PersonUtils.getPersonDurchBenutzername(BENUTZERNAME, schulerService);

		// Then
		assertThat(person, is(instanceOf(Schuler.class)));
	}

	@Test(expected = FalschServiceException.class)
	public void getPersonDurchBenutzerNameMitFalschObjekt() {
		// Given
		Object object = new Object();

		// When
		PersonUtils.getPersonDurchBenutzername(BENUTZERNAME, object);

	}

//	@Test
//	public void erstellenPersonMitValidBenutzerAttributeTest() {
//		// Given
//		schuler = new Schuler();
//		Rolle rolle = new Rolle(1, RolleTyp.ROLLE_SCHULER);
//		Benutzer benutzer = new Benutzer(123L, "SomeName123", "somePass", rolle);
//		schuler.setBenutzer(benutzer);
//
//		// When
//		when(rolleService.finden(RolleTyp.ROLLE_SCHULER)).thenReturn(rolle);
//		when(RolleUtils.erstellenValidRolleFuerPerson(schuler, rolleService)).thenReturn(rolle);
//		when(BenutzerUtils.erstellenBenutzerMitRolle(rolle)).thenReturn(benutzer);
//		doNothing().when(schuler).setBenutzer(benutzer);
//		Object person = PersonUtils.erstellenPersonMitValidBenutzerAttribute(schuler, rolleService);
//
//		// Then
//		assertThat(person, hasProperty("benutzer", equalTo(benutzer)));
//	}

}
