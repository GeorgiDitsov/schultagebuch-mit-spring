package com.proxiad.schultagebuch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.exception.FalschServiceException;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MenschUtilsIT {

	@Mock
	private Schuler schuler;

	@Test
	public void erstellenRichtigeMensch() {
		// Given
		schuler = new Schuler();

		// When
		Object mensch = MenschUtils.erstellenMenschMitRichtigeBenutzer(schuler, new Benutzer());

		// Then
		assertThat(mensch, is(instanceOf(Schuler.class)));
	}

	// Then
	@Test(expected = FalschServiceException.class)
	public void erstellenFalschMensch() {
		// Given
		Object objekt = new Object();

		// When
		MenschUtils.erstellenMenschMitRichtigeBenutzer(objekt, new Benutzer());
	}

}
