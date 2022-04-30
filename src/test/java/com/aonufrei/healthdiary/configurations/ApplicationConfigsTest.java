package com.aonufrei.healthdiary.configurations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationConfigsTest {

	@Test
	public void testConfig() {
		assertEquals(10, ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE);
	}

}