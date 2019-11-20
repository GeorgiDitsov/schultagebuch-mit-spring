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
public class MenschUtilsIT {

	private static final String BENUTZERNAME = "SomeBenutzername";

	@Mock
	private SchulerService schulerService;

	@Mock
	private RolleService rolleService;

	@Mock
	private Schuler schuler;

	@Test
	public void getMenschDurchBenutzerNameTest() {
		// Given
		schuler = new Schuler();

		// When
		when(schulerService.findeDurchBenutzername(BENUTZERNAME)).thenReturn(schuler);
		Object mensch = MenschUtils.getMenschDurchBenutzername(BENUTZERNAME, schulerService);

		// Then
		assertThat(mensch, is(instanceOf(Schuler.class)));
	}

	@Test(expected = FalschServiceException.class)
	public void getMenschDurchBenutzerNameMitFalschObjekt() {
		// Given
		Object objekt = new Object();

		// When
		MenschUtils.getMenschDurchBenutzername(BENUTZERNAME, objekt);

	}

	@Test
	public void erstellenRichtigeMensch() {
		// Given
		schuler = new Schuler();

		// When
		Object mensch = MenschUtils.erstellenMenschMitRichtigeBenutzer(schuler, new Benutzer());

		// Then
		assertThat(mensch, is(instanceOf(Schuler.class)));
	}

	@Test(expected = FalschServiceException.class)
	public void erstellenFalschMensch() {
		// Given
		Object objekt = new Object();

		// When
		MenschUtils.erstellenMenschMitRichtigeBenutzer(objekt, new Benutzer());
	}

}
