package com.proxiad.schultagebuch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.exception.FalschServiceException;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class KennzeichenUtilsIT {

	@Mock
	private Lehrer lehrer;

	@Test
	public void personKennzeichenTest() {
		// Given
		when(lehrer.getName()).thenReturn("Nikola Penev");
		when(lehrer.getPin()).thenReturn("1010101010");

		// When
		String personKennzeichen = KennzeichenUtils.menschKennzeichen(lehrer);

		// Then
		assertThat(personKennzeichen, is(equalTo("Nikola Penev, 101010****")));
	}

	// Then
	@Test(expected = FalschServiceException.class)
	public void personKennzeichenMitFalschObjekt() {
		// Given
		Object objekt = new Object();

		// When
		KennzeichenUtils.menschKennzeichen(objekt);
	}

}
