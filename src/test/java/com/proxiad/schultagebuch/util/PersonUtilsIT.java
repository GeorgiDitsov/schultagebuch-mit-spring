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

import com.proxiad.schultagebuch.entity.Benutzer;
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

	@Test
	public void erstellenValidInstanceOfPerson() {
		// Given
		schuler = new Schuler();

		// When
		Object schulerFound = PersonUtils.erstellenPersonMitValidBenutzerAttribute(schuler, new Benutzer());

		// Then
		assertThat(schulerFound, is(instanceOf(Schuler.class)));
	}

	@Test(expected = FalschServiceException.class)
	public void eestellenInvalidInstanceOfPerson() {
		// Given
		Object object = new Object();

		// When
		PersonUtils.erstellenPersonMitValidBenutzerAttribute(object, new Benutzer());
	}

}
