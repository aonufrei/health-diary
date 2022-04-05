package com.aonufrei.healthdiary.utils;

import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.AimStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class ModelDtoUtilTest {

	@Test
	public void testUpdateModel() {
		LocalDate mentioned = LocalDate.now();
		Aim aim = Aim.builder()
				.id(1)
				.personId(2)
				.status(AimStatus.CREATED)
				.mentioned(mentioned)
				.build();

		AimInDto aimInDto = AimInDto.builder()
				.personId(3)
				.status(AimStatus.FAILED)
				.build();

		ModelDtoUtil.updateModel(aim, aimInDto);

		Assertions.assertEquals(1, aim.getId());
		Assertions.assertEquals(3, aim.getPersonId());
		Assertions.assertEquals(AimStatus.FAILED, aim.getStatus());
		Assertions.assertEquals(mentioned, aim.getMentioned());
	}

}
