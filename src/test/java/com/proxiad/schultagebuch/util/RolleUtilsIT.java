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
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.service.RolleService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RolleUtilsIT {

	@Mock
	private RolleService rolleService;

	@Test
	public void erstellenValidRolleFuerPersonTest() {
		// Given
		Lehrer lehrer = new Lehrer();
		Rolle rolle = new Rolle(4, RolleTyp.ROLLE_LEHRER);

		// When
		when(rolleService.finden(RolleTyp.ROLLE_LEHRER)).thenReturn(rolle);
		Rolle foundRole = RolleUtils.erstellenValidRolleFuerPerson(lehrer, rolleService);

		// Then
		assertThat(foundRole, is(equalTo(rolle)));
	}

	@Test
	public void falschObjektPerson() {
		// Given
		Object random = new Object();

		// When
		Rolle foundRole = RolleUtils.erstellenValidRolleFuerPerson(random, rolleService);

		// Then
		assertThat(foundRole, is(equalTo(null)));

	}

}
