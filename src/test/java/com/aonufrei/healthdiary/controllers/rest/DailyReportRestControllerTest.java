package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.services.DailyReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DailyReportRestControllerTest {

	@MockBean
	private DailyReportService dailyReportService;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/daily-report";

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetDailyReportForAdmin() throws Exception {
		testGetDailyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetCaloriesByDaysForAdmin() throws Exception {
		testGetCaloriesByDays();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetDailyReportForUser() throws Exception {
		testGetDailyReport();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetCaloriesByDaysForUser() throws Exception {
		testGetCaloriesByDays();
	}


	private void testGetDailyReport() throws Exception {
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


	private void testGetCaloriesByDays() throws Exception {
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