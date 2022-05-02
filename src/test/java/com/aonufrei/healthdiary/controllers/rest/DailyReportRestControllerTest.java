package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.services.BodyReportService;
import com.aonufrei.healthdiary.services.DailyReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DailyReportRestController.class)
class DailyReportRestControllerTest {

	@MockBean
	private DailyReportService dailyReportService;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/daily-report";

	@Test
	void testGetDailyReport() throws Exception {
		assertNotNull(dailyReportService);

		when(dailyReportService.getDailyReportCalories(anyInt(), any(LocalDate.class))).thenReturn(null);
		mvc.perform(get(url + "/day").param("person", "1")
				.param("date", "2022-01-01"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/day").param("person", "1")
						.param("date", "asdfasdf"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/day")
						.param("date", "2022-01-01"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/day").param("person", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/day"))
				.andExpect(status().isBadRequest());

	}

	@Test
	void testGetCaloriesByDays() throws Exception {
		assertNotNull(dailyReportService);

		when(dailyReportService.getDailyReportCalories(anyInt(), any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/days").param("person", "1")
						.param("from_date", "2022-01-01")
						.param("to_date", "2022-02-01"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/days").param("person", "1")
						.param("from_date", "2022-02-01")
						.param("to_date", "2022-01-01"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/days")
						.param("from_date", "2022-01-01")
						.param("to_date", "2022-02-01"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/days").param("person", "1")
						.param("from_date", "2022-01-01"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/days").param("person", "1")
						.param("to_date", "2022-02-01"))
				.andExpect(status().isBadRequest());
	}

}