package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.repositories.BodyReportRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodyReportServiceTest {

	@Mock
	private BodyReportRepository bodyReportRepository;

	@InjectMocks
	private BodyReportService bodyReportService;

	private final List<BodyReport> bodyReports = new ArrayList<BodyReport>() {{
		add(BodyReport.builder().id(1).type(BodyReportType.HEIGHT).personId(1).build());
		add(BodyReport.builder().id(2).type(BodyReportType.HEIGHT).personId(2).build());
		add(BodyReport.builder().id(3).type(BodyReportType.HEIGHT).personId(2).build());
		add(BodyReport.builder().id(4).type(BodyReportType.HEIGHT).personId(2).build());
		add(BodyReport.builder().id(5).type(BodyReportType.HEIGHT).personId(3).build());
	}};

	@Test
	public void getBodyReportsByPersonAndType() {
		assertNotNull(bodyReportRepository);
		assertNotNull(bodyReportService);
		List<BodyReport> expectedResults = bodyReports.stream().filter(it -> it.getPersonId().equals(2)).collect(Collectors.toList());
		lenient().when(bodyReportRepository.getBodyReportByPersonIdAndType(2, BodyReportType.HEIGHT)).thenReturn(expectedResults);
		lenient().when(bodyReportRepository.getBodyReportByPersonIdAndType(4, BodyReportType.HEIGHT)).thenReturn(Collections.emptyList());

		assertEquals(expectedResults.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList()),
				bodyReportService.getBodyReportsByPersonAndType(2, BodyReportType.HEIGHT));
		assertEquals(Collections.EMPTY_LIST, bodyReportService.getBodyReportsByPersonAndType(4, BodyReportType.HEIGHT));
	}
}