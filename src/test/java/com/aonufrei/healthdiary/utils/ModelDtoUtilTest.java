package com.aonufrei.healthdiary.utils;

import com.aonufrei.healthdiary.dtos.*;
import com.aonufrei.healthdiary.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

	@Test
	void testInDtoToModelWhenNull() {
		AimInDto aimInDto = null;
		BodyReportInDto bodyReportInDto = null;
		FoodInDto foodInDto = null;
		FoodReportInDto foodReportInDto = null;
		LikeInDto likeInDto = null;
		MetricInDto metricInDto = null;
		PersonInDto personInDto = null;
		PostInDto postInDto = null;
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(aimInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(bodyReportInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(foodInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(foodReportInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(likeInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(metricInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(personInDto));
		Assertions.assertNull(ModelDtoUtil.inDtoToModel(postInDto));
	}

	@Test
	void testUpdateModelWhenNull() {
		Aim aim = new Aim();
		BodyReport bodyReport = new BodyReport();
		Food food = new Food();
		FoodReport foodReport = new FoodReport();
		Like like = new Like();
		Metric metric = new Metric();
		Person person = new Person();
		Post post = new Post();

		Aim aim1 = new Aim();
		BodyReport bodyReport1 = new BodyReport();
		Food food1 = new Food();
		FoodReport foodReport1 = new FoodReport();
		Like like1 = new Like();
		Metric metric1 = new Metric();
		Person person1 = new Person();
		Post post1 = new Post();

		AimInDto aimInDto = null;
		BodyReportInDto bodyReportInDto = null;
		FoodInDto foodInDto = null;
		FoodReportInDto foodReportInDto = null;
		LikeInDto likeInDto = null;
		MetricInDto metricInDto = null;
		PersonInDto personInDto = null;
		PostInDto postInDto = null;

		ModelDtoUtil.updateModel(aim, aimInDto);
		ModelDtoUtil.updateModel(bodyReport, bodyReportInDto);
		ModelDtoUtil.updateModel(food, foodInDto);
		ModelDtoUtil.updateModel(foodReport, foodReportInDto);
		ModelDtoUtil.updateModel(like, likeInDto);
		ModelDtoUtil.updateModel(metric, metricInDto);
		ModelDtoUtil.updateModel(person, personInDto);
		ModelDtoUtil.updateModel(post, postInDto);

		Assertions.assertEquals(aim1, aim);
		Assertions.assertEquals(bodyReport1, bodyReport);
		Assertions.assertEquals(food1, food);
		Assertions.assertEquals(foodReport1, foodReport);
		Assertions.assertEquals(like1, like);
		Assertions.assertEquals(metric1, metric);
		Assertions.assertEquals(person1, person);
		Assertions.assertEquals(post1, post);
	}

	@Test
	void testModelToDtoWhenNull() {
		Aim aim = null;
		BodyReport bodyReport = null;
		Food food = null;
		FoodReport foodReport = null;
		Like like = null;
		Metric metric = null;
		Person person = null;
		Post post = null;
		Assertions.assertNull(ModelDtoUtil.modelToDto(aim));
		Assertions.assertNull(ModelDtoUtil.modelToDto(bodyReport));
		Assertions.assertNull(ModelDtoUtil.modelToDto(food));
		Assertions.assertNull(ModelDtoUtil.modelToDto(foodReport));
		Assertions.assertNull(ModelDtoUtil.modelToDto(like));
		Assertions.assertNull(ModelDtoUtil.modelToDto(metric));
		Assertions.assertNull(ModelDtoUtil.modelToDto(person));
		Assertions.assertNull(ModelDtoUtil.modelToDto(post));
	}

	@Test
	void testAimInDtoToModel() {
		AimInDto aimInDto = new AimInDto();
		aimInDto.setTargetWeight(80.0f);
		aimInDto.setPersonId(1);
		aimInDto.setMentioned(null);
		aimInDto.setStatus(AimStatus.CREATED);

		Aim aim = new Aim();
		aim.setTargetWeight(80.0f);
		aim.setPersonId(1);
		aim.setMentioned(null);
		aim.setStatus(AimStatus.CREATED);

		Assertions.assertEquals(aim, ModelDtoUtil.inDtoToModel(aimInDto));
		LocalDate now = LocalDate.now();
		aimInDto.setMentioned(now);
		aim.setMentioned(now);
		Assertions.assertEquals(aim, ModelDtoUtil.inDtoToModel(aimInDto));
		aim.setMentioned(now.plusDays(1));
		Assertions.assertNotEquals(aim, ModelDtoUtil.inDtoToModel(aimInDto));
	}

	@Test
	void testBodyReportInDtoToModel() {
		BodyReportInDto bodyReportInDto = new BodyReportInDto();
		bodyReportInDto.setType(BodyReportType.HEIGHT);
		bodyReportInDto.setPersonId(1);
		bodyReportInDto.setValue(null);
		BodyReport bodyReport = new BodyReport();
		bodyReport.setType(BodyReportType.HEIGHT);
		bodyReport.setPersonId(1);
		bodyReport.setValue(null);

		Assertions.assertEquals(bodyReport, ModelDtoUtil.inDtoToModel(bodyReportInDto));
		bodyReportInDto.setValue(1f);
		bodyReport.setValue(1f);
		Assertions.assertEquals(bodyReport, ModelDtoUtil.inDtoToModel(bodyReportInDto));
		bodyReport.setValue(2f);
		Assertions.assertNotEquals(bodyReport, ModelDtoUtil.inDtoToModel(bodyReportInDto));
	}

	@Test
	void testFoodInDtoToModel() {
		Food food = new Food();
		FoodInDto foodInDto = new FoodInDto();

		Assertions.assertEquals(food, ModelDtoUtil.inDtoToModel(foodInDto));
		food.setName("food");
		foodInDto.setName("food");
		Assertions.assertEquals(food, ModelDtoUtil.inDtoToModel(foodInDto));
		foodInDto.setName("food2");
		Assertions.assertNotEquals(food, ModelDtoUtil.inDtoToModel(foodInDto));
	}

	@Test
	void testFoodReportInDtoToModel() {
		FoodReport foodReport = new FoodReport();
		foodReport.setFoodId(1);
		foodReport.setMetricId(1);
		foodReport.setType(FoodReportType.BREAKFAST);
		foodReport.setPersonId(1);
		foodReport.setReportedDate(LocalDate.now());

		FoodReportInDto foodReportInDto = new FoodReportInDto();
		foodReportInDto.setFoodId(1);
		foodReportInDto.setMetricId(1);
		foodReportInDto.setType(FoodReportType.BREAKFAST);
		foodReportInDto.setPersonId(1);
		foodReportInDto.setReportedDate(LocalDate.now());

		Assertions.assertEquals(foodReport, ModelDtoUtil.inDtoToModel(foodReportInDto));
		foodReport.setAmount(11);
		foodReportInDto.setAmount(11);
		Assertions.assertEquals(foodReport, ModelDtoUtil.inDtoToModel(foodReportInDto));
		foodReportInDto.setAmount(12);
		Assertions.assertNotEquals(foodReport, ModelDtoUtil.inDtoToModel(foodReportInDto));

	}

	@Test
	void testLikeInDtoToModel() {
		Like like = new Like();
		like.setAuthorId(1);
		LikeInDto likeInDto = new LikeInDto();
		likeInDto.setAuthorId(1);

		Assertions.assertEquals(like, ModelDtoUtil.inDtoToModel(likeInDto));
		like.setPostId(1);
		likeInDto.setPostId(1);
		Assertions.assertEquals(like, ModelDtoUtil.inDtoToModel(likeInDto));
		like.setPostId(2);
		Assertions.assertNotEquals(like, ModelDtoUtil.inDtoToModel(likeInDto));
	}

	@Test
	void testMetricInDtoToModel() {
		Metric metric = new Metric();
		metric.setType(FoodMetricType.CUP);
		metric.setCarbs(23f);
		MetricInDto metricInDto = new MetricInDto();
		metricInDto.setType(FoodMetricType.CUP);
		metricInDto.setCarbs(23f);

		Assertions.assertEquals(metric, ModelDtoUtil.inDtoToModel(metricInDto));
		metric.setCalories(12f);
		metricInDto.setCalories(12f);
		Assertions.assertEquals(metric, ModelDtoUtil.inDtoToModel(metricInDto));
		metricInDto.setFoodId(1);
		Assertions.assertNotEquals(metric, ModelDtoUtil.inDtoToModel(metricInDto));
	}

	@Test
	void testPersonInDtoToModel() {
		Person person = new Person();
		person.setName("Test person");
		person.setEmail("test@mail.com");
		person.setGender(Gender.MALE);
		person.setImagePath("image.png");
		PersonInDto personInDto = new PersonInDto();
		personInDto.setName("Test person");
		personInDto.setEmail("test@mail.com");
		personInDto.setGender(Gender.MALE);
		personInDto.setImagePath("image.png");

		Assertions.assertEquals(person, ModelDtoUtil.inDtoToModel(personInDto));
		person.setGender(Gender.FEMALE);
		personInDto.setGender(Gender.FEMALE);
		Assertions.assertEquals(person, ModelDtoUtil.inDtoToModel(personInDto));
		person.setName("Other test person");
		Assertions.assertNotEquals(person, ModelDtoUtil.inDtoToModel(personInDto));
	}

	@Test
	void testPostInDtoToModel() {
		Post post = new Post();
		post.setContent("Content");
		post.setImagePath("image");
		post.setAuthorId(1);
		PostInDto postInDto = new PostInDto();
		postInDto.setContent("Content");
		postInDto.setImagePath("image");
		postInDto.setAuthorId(1);

		Assertions.assertEquals(post, ModelDtoUtil.inDtoToModel(postInDto));
		post.setContent("Content2");
		postInDto.setContent("Content2");
		Assertions.assertEquals(post, ModelDtoUtil.inDtoToModel(postInDto));
		post.setContent("Content");
		Assertions.assertNotEquals(post, ModelDtoUtil.inDtoToModel(postInDto));

	}

	@Test
	void testUpdateAimModel() {
		AimInDto aimInDto = new AimInDto();
		aimInDto.setTargetWeight(80.0f);
		aimInDto.setPersonId(1);
		aimInDto.setStatus(null);

		Aim aimBefore = new Aim();
		aimBefore.setMentioned(null);
		aimBefore.setStatus(AimStatus.FAILED);

		Aim aimAfter = new Aim();
		aimAfter.setTargetWeight(80.0f);
		aimAfter.setPersonId(1);
		aimAfter.setStatus(AimStatus.FAILED);

		ModelDtoUtil.updateModel(aimBefore, aimInDto);
		Assertions.assertEquals(aimAfter, aimBefore);
	}

	@Test
	void testBodyReportUpdateModel() {
		BodyReportInDto bodyReportInDto = new BodyReportInDto();
		bodyReportInDto.setType(BodyReportType.HEIGHT);
		bodyReportInDto.setPersonId(1);
		bodyReportInDto.setValue(null);

		BodyReport bodyReportBefore = new BodyReport();
		bodyReportBefore.setValue(12f);

		BodyReport bodyReportAfter = new BodyReport();
		bodyReportAfter.setType(BodyReportType.HEIGHT);
		bodyReportAfter.setPersonId(1);
		bodyReportAfter.setValue(12f);

		ModelDtoUtil.updateModel(bodyReportBefore, bodyReportInDto);
		Assertions.assertEquals(bodyReportAfter, bodyReportBefore);
	}

	@Test
	void testFoodUpdateModel() {
		FoodInDto foodInDto = new FoodInDto();
		foodInDto.setName("Food");
		Food foodBefore = new Food();
		foodBefore.setName("Food before");
		Food foodAfter = new Food();
		foodAfter.setName("Food");

		ModelDtoUtil.updateModel(foodBefore, foodInDto);
		Assertions.assertEquals(foodAfter, foodBefore);
	}

	@Test
	void testFoodReportUpdateModel() {
		FoodReportInDto foodReportInDto = new FoodReportInDto();
		foodReportInDto.setAmount(12);
		foodReportInDto.setFoodId(1);
		foodReportInDto.setType(FoodReportType.BREAKFAST);
		FoodReport foodReportBefore = new FoodReport();
		foodReportBefore.setAmount(12);
		foodReportBefore.setReportedDate(LocalDate.now());
		FoodReport foodReportAfter = new FoodReport();
		foodReportAfter.setAmount(12);
		foodReportAfter.setFoodId(1);
		foodReportAfter.setType(FoodReportType.BREAKFAST);
		foodReportAfter.setReportedDate(LocalDate.now());

		ModelDtoUtil.updateModel(foodReportBefore, foodReportInDto);
		Assertions.assertEquals(foodReportBefore, foodReportAfter);
	}

	@Test
	void testLikeUpdateModel() {
		LikeInDto likeInDto = new LikeInDto();
		likeInDto.setPostId(2);
		Like likeBefore = new Like();
		likeBefore.setAuthorId(1);
		likeBefore.setPostId(1);
		Like likeAfter = new Like();
		likeAfter.setAuthorId(1);
		likeAfter.setPostId(2);

		ModelDtoUtil.updateModel(likeBefore, likeInDto);
		Assertions.assertEquals(likeBefore, likeAfter);
	}

	@Test
	void testMetricUpdateModel() {
		MetricInDto metricInDto = new MetricInDto();
		metricInDto.setFoodId(1);
		metricInDto.setCalories(12f);
		metricInDto.setCarbs(12f);
		Metric metricBefore = new Metric();
		metricBefore.setFoodId(2);
		metricBefore.setCalories(24f);
		metricBefore.setCarbs(23f);
		metricBefore.setType(FoodMetricType.CUP);
		Metric metricAfter = new Metric();
		metricAfter.setFoodId(1);
		metricAfter.setCalories(12f);
		metricAfter.setCarbs(12f);
		metricAfter.setType(FoodMetricType.CUP);

		ModelDtoUtil.updateModel(metricBefore, metricInDto);
		Assertions.assertEquals(metricAfter, metricBefore);
	}

	@Test
	void testPersonUpdateModel() {
		PersonInDto personInDto = new PersonInDto();
		personInDto.setName("Person");
		personInDto.setGender(Gender.FEMALE);
		personInDto.setImagePath("image");
		Person personBefore = new Person();
		personBefore.setGender(Gender.MALE);
		personBefore.setEmail("person@gmail.com");
		Person personAfter = new Person();
		personAfter.setName("Person");
		personAfter.setGender(Gender.FEMALE);
		personAfter.setImagePath("image");
		personAfter.setEmail("person@gmail.com");

		ModelDtoUtil.updateModel(personBefore, personInDto);
		Assertions.assertEquals(personBefore, personAfter);
	}

	@Test
	void testPostUpdateModel() {
		PostInDto postInDto = new PostInDto();
		postInDto.setAuthorId(1);
		postInDto.setContent("Content");
		Post postBefore = new Post();
		postBefore.setAuthorId(2);
		postBefore.setImagePath("image");
		Post postAfter = new Post();
		postAfter.setAuthorId(1);
		postAfter.setContent("Content");
		postAfter.setImagePath("image");

		ModelDtoUtil.updateModel(postBefore, postInDto);
		Assertions.assertEquals(postAfter, postBefore);
	}

	@Test
	void testAimModelToDto() {
		Person person = Person.builder().name("Person").imagePath("image").build();
		PersonDto personDto = ModelDtoUtil.modelToDto(person);
		Aim aim = new Aim();
		aim.setId(1);
		aim.setPerson(person);
		aim.setMentioned(LocalDate.now());
		aim.setStatus(AimStatus.CREATED);
		aim.setModifiedAt(LocalDateTime.now());
		AimDto aimDto = new AimDto();
		aimDto.setId(1);
		aimDto.setPersonDto(personDto);
		aimDto.setMentioned(aim.getMentioned());
		aimDto.setStatus(AimStatus.CREATED);
		aimDto.setModifiedAt(aim.getModifiedAt());

		Assertions.assertEquals(aimDto, ModelDtoUtil.modelToDto(aim));
		aim.setPerson(null);
		Assertions.assertNotEquals(aimDto, ModelDtoUtil.modelToDto(aim));
		aimDto.setPersonDto(null);
		Assertions.assertEquals(aimDto, ModelDtoUtil.modelToDto(aim));
		aim.setModifiedAt(null);
		aimDto.setModifiedAt(null);
		Assertions.assertEquals(aimDto, ModelDtoUtil.modelToDto(aim));
	}

	@Test
	void testBodyReportModelToDto() {
		BodyReport bodyReport = new BodyReport();
		bodyReport.setId(1);
		bodyReport.setValue(1f);
		bodyReport.setType(BodyReportType.HEIGHT);
		bodyReport.setPersonId(1);
		bodyReport.setLoggedTime(LocalDateTime.now());
		bodyReport.setCreatedAt(LocalDateTime.now());
		bodyReport.setModifiedAt(LocalDateTime.now());

		BodyReportDto bodyReportDto = new BodyReportDto();
		bodyReportDto.setId(1);
		bodyReportDto.setValue(1f);
		bodyReportDto.setType(BodyReportType.HEIGHT);
		bodyReportDto.setPersonId(1);
		bodyReportDto.setLoggedTime(bodyReport.getLoggedTime());
		bodyReportDto.setCreatedAt(bodyReport.getCreatedAt());
		bodyReportDto.setModifiedAt(bodyReport.getModifiedAt());

		Assertions.assertEquals(bodyReportDto, ModelDtoUtil.modelToDto(bodyReport));
		bodyReport.setPersonId(null);
		Assertions.assertNotEquals(bodyReportDto, ModelDtoUtil.modelToDto(bodyReport));
		bodyReportDto.setPersonId(null);
		Assertions.assertEquals(bodyReportDto, ModelDtoUtil.modelToDto(bodyReport));
	}

	@Test
	void testFoodModelToDto() {
		Metric metric1 = Metric.builder().foodId(1).calories(12f).build();
		Metric metric2 = Metric.builder().foodId(1).carbs(32f).build();
		Food food = new Food();
		food.setId(1);
		food.setName("Food");
		food.setMetrics(new HashSet<Metric>() {{
			add(metric1);
			add(metric2);
		}});
		food.setCreatedAt(LocalDateTime.now());
		food.setModifiedAt(LocalDateTime.now());

		FoodDto foodDto = new FoodDto();
		foodDto.setId(food.getId());
		foodDto.setName(food.getName());
		foodDto.setMetrics(new HashSet<MetricDto>() {{
			add(ModelDtoUtil.modelToDto(metric1));
			add(ModelDtoUtil.modelToDto(metric2));
		}});
		foodDto.setCreatedAt(food.getCreatedAt());
		foodDto.setModifiedAt(food.getModifiedAt());

		Assertions.assertEquals(foodDto, ModelDtoUtil.modelToDto(food));
		food.setMetrics(null);
		Assertions.assertNotEquals(foodDto, ModelDtoUtil.modelToDto(food));
		foodDto.setMetrics(null);
		Assertions.assertEquals(foodDto, ModelDtoUtil.modelToDto(food));
	}

	@Test
	void testFoodReportModelToDto() {
		Food food = Food.builder().id(1).name("food").build();
		Metric metric = Metric.builder().id(1).foodId(1).calories(12f).build();
		FoodReport foodReport = new FoodReport();
		foodReport.setId(12);
		foodReport.setAmount(10);
		foodReport.setFood(food);
		foodReport.setMetric(metric);
		foodReport.setType(FoodReportType.BREAKFAST);
		foodReport.setPersonId(1);
		foodReport.setReportedDate(LocalDate.now());
		foodReport.setModifiedAt(LocalDateTime.now());

		FoodReportDto foodReportDto = new FoodReportDto();
		foodReportDto.setId(12);
		foodReportDto.setAmount(10);
		foodReportDto.setFood(ModelDtoUtil.modelToDto(food));
		foodReportDto.setMetric(ModelDtoUtil.modelToDto(metric));
		foodReportDto.setType(FoodReportType.BREAKFAST);
		foodReportDto.setPersonId(1);
		foodReportDto.setReportedDate(foodReport.getReportedDate());
		foodReportDto.setModifiedAt(foodReport.getModifiedAt());

		Assertions.assertEquals(foodReportDto, ModelDtoUtil.modelToDto(foodReport));
		foodReport.setFood(null);
		Assertions.assertNotEquals(foodReportDto, ModelDtoUtil.modelToDto(foodReport));
		foodReportDto.setFood(null);
		Assertions.assertEquals(foodReportDto, ModelDtoUtil.modelToDto(foodReport));
		foodReport.setMetric(null);
		Assertions.assertNotEquals(foodReportDto, ModelDtoUtil.modelToDto(foodReport));
		foodReportDto.setMetric(null);
		Assertions.assertEquals(foodReportDto, ModelDtoUtil.modelToDto(foodReport));
	}

	@Test
	void testLikeModelToDto() {
		Like like = new Like();
		like.setId(1);
		like.setPostId(1);
		like.setAuthorId(1);
		like.setCreatedAt(LocalDateTime.now());
		like.setModifiedAt(LocalDateTime.now());

		LikeDto likeDto = new LikeDto();
		likeDto.setId(1);
		likeDto.setPostId(1);
		likeDto.setAuthorId(1);
		likeDto.setCreatedAt(like.getCreatedAt());
		likeDto.setModifiedAt(like.getModifiedAt());

		Assertions.assertEquals(likeDto, ModelDtoUtil.modelToDto(like));
		like.setAuthorId(null);
		Assertions.assertNotEquals(likeDto, ModelDtoUtil.modelToDto(like));
		likeDto.setAuthorId(null);
		Assertions.assertEquals(likeDto, ModelDtoUtil.modelToDto(like));
	}

	@Test
	void testMetricModelToDto() {
		Metric metric = new Metric();
		metric.setId(1);
		metric.setType(FoodMetricType.CUP);
		metric.setCalories(12f);
		metric.setCarbs(12f);
		metric.setProtein(12f);
		metric.setFat(12f);
		metric.setFoodId(1);
		metric.setCreatedAt(LocalDateTime.now());
		metric.setModifiedAt(LocalDateTime.now());

		MetricDto metricDto = new MetricDto();
		metricDto.setId(1);
		metricDto.setType(FoodMetricType.CUP);
		metricDto.setCalories(12f);
		metricDto.setCarbs(12f);
		metricDto.setProtein(12f);
		metricDto.setFat(12f);
		metricDto.setFoodId(1);
		metricDto.setCreatedAt(metric.getCreatedAt());
		metricDto.setModifiedAt(metric.getModifiedAt());

		Assertions.assertEquals(metricDto, ModelDtoUtil.modelToDto(metric));
		metric.setFoodId(null);
		Assertions.assertNotEquals(metricDto, ModelDtoUtil.modelToDto(metric));
		metricDto.setFoodId(null);
		Assertions.assertEquals(metricDto, ModelDtoUtil.modelToDto(metric));
	}

	@Test
	void testPersonModelToDto() {
		Person person = new Person();
		person.setId(1);
		person.setName("Test person");
		person.setEmail("email");
		person.setDob(LocalDate.now().minusYears(20));
		person.setGender(Gender.MALE);
		person.setActivity(PhysicalActivity.LIGHTLY_ACTIVE);
		person.setImagePath("image");
		person.setCreatedAt(LocalDateTime.now());
		person.setModifiedAt(LocalDateTime.now());

		PersonDto personDto = new PersonDto();
		personDto.setId(1);
		personDto.setName("Test person");
		personDto.setEmail("email");
		personDto.setDob(person.getDob());
		personDto.setGender(Gender.MALE);
		personDto.setActivity(PhysicalActivity.LIGHTLY_ACTIVE);
		personDto.setImagePath("image");
		personDto.setCreatedAt(person.getCreatedAt());
		personDto.setModifiedAt(person.getModifiedAt());

		Assertions.assertEquals(personDto, ModelDtoUtil.modelToDto(person));
		person.setGender(null);
		Assertions.assertNotEquals(personDto, ModelDtoUtil.modelToDto(person));
		personDto.setGender(null);
		Assertions.assertEquals(personDto, ModelDtoUtil.modelToDto(person));
	}

	@Test
	void testPostModelToDto() {
		Set<Like> likes = new HashSet<>();
		likes.add(Like.builder().id(1).build());
		likes.add(Like.builder().id(2).build());
		likes.add(Like.builder().id(3).build());
		Post post = new Post();
		post.setId(1);
		post.setContent("Content");
		post.setImagePath("image");
		post.setLikes(likes);
		post.setAuthorId(1);
		post.setCreatedAt(LocalDateTime.now());
		post.setModifiedAt(LocalDateTime.now());

		PostDto postDto = new PostDto();
		postDto.setId(1);
		postDto.setContent("Content");
		postDto.setImagePath("image");
		postDto.setLikesCount(3);
		postDto.setAuthorId(1);
		postDto.setCreatedAt(post.getCreatedAt());
		postDto.setModifiedAt(post.getModifiedAt());

		Assertions.assertEquals(postDto, ModelDtoUtil.modelToDto(post));
		post.setLikes(new HashSet<>());
		postDto.setLikesCount(0);
		Assertions.assertEquals(postDto, ModelDtoUtil.modelToDto(post));
		post.setLikes(null);
		Assertions.assertEquals(postDto, ModelDtoUtil.modelToDto(post));
	}
}
