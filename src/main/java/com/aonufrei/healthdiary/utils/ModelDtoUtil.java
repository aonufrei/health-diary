package com.aonufrei.healthdiary.utils;

import com.aonufrei.healthdiary.dtos.*;
import com.aonufrei.healthdiary.models.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelDtoUtil {

	//In dtos to models

	public static Aim inDtoToModel(@NotNull AimInDto inDto) {
		if (inDto == null) return null;

		return Aim.builder()
				.targetWeight(inDto.getTargetWeight())
				.personId(inDto.getPersonId())
				.mentioned(inDto.getMentioned())
				.status(inDto.getStatus())
				.build();
	}

	public static BodyReport inDtoToModel(@NotNull BodyReportInDto inDto) {
		if (inDto == null) return null;

		return BodyReport.builder()
				.value(inDto.getValue())
				.type(inDto.getType())
				.loggedTime(inDto.getLoggedTime())
				.personId(inDto.getPersonId())
				.build();
	}

	public static Food inDtoToModel(@NotNull FoodInDto inDto) {
		if (inDto == null) return null;

		return Food.builder()
				.name(inDto.getName())
				.build();
	}

	public static FoodReport inDtoToModel(@NotNull FoodReportInDto inDto) {
		if (inDto == null) return null;

		return FoodReport.builder()
				.amount(inDto.getAmount())
				.foodId(inDto.getFoodId())
				.metricId(inDto.getMetricId())
				.type(inDto.getType())
				.personId(inDto.getPersonId())
				.reportedDate(inDto.getReportedDate())
				.build();
	}

	public static Like inDtoToModel(@NotNull LikeInDto inDto) {
		if (inDto == null) return null;

		return Like.builder()
				.authorId(inDto.getAuthorId())
				.postId(inDto.getPostId())
				.build();
	}

	public static Metric inDtoToModel(@NotNull MetricInDto inDto) {
		if (inDto == null) return null;

		return Metric.builder()
				.type(inDto.getType())
				.calories(inDto.getCalories())
				.carbs(inDto.getCarbs())
				.protein(inDto.getProtein())
				.fat(inDto.getFat())
				.foodId(inDto.getFoodId())
				.build();
	}

	public static Person inDtoToModel(@NotNull PersonInDto inDto) {
		if (inDto == null) return null;

		return Person.builder()
				.name(inDto.getName())
				.email(inDto.getEmail())
				.dob(inDto.getDob())
				.gender(inDto.getGender())
				.activity(inDto.getActivity())
				.imagePath(inDto.getImagePath())
				.build();
	}

	public static Post inDtoToModel(@NotNull PostInDto inDto) {
		if (inDto == null) return null;

		return Post.builder()
				.content(inDto.getContent())
				.imagePath(inDto.getImagePath())
				.authorId(inDto.getAuthorId())
				.build();
	}

	// Update models

	public static void updateModel(@NotNull Aim model, @NotNull AimInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getTargetWeight()).ifPresent(model::setTargetWeight);
		Optional.ofNullable(inDto.getPersonId()).ifPresent(model::setPersonId);
		Optional.ofNullable(inDto.getMentioned()).ifPresent(model::setMentioned);
		Optional.ofNullable(inDto.getStatus()).ifPresent(model::setStatus);
	}

	public static void updateModel(@NotNull BodyReport model, @NotNull BodyReportInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getValue()).ifPresent(model::setValue);
		Optional.ofNullable(inDto.getType()).ifPresent(model::setType);
		Optional.ofNullable(inDto.getLoggedTime()).ifPresent(model::setLoggedTime);
		Optional.ofNullable(inDto.getPersonId()).ifPresent(model::setPersonId);
	}

	public static void updateModel(@NotNull Food model, @NotNull FoodInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getName()).ifPresent(model::setName);
	}

	public static void updateModel(@NotNull FoodReport model, @NotNull FoodReportInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getAmount()).ifPresent(model::setAmount);
		Optional.ofNullable(inDto.getFoodId()).ifPresent(model::setFoodId);
		Optional.ofNullable(inDto.getMetricId()).ifPresent(model::setMetricId);
		Optional.ofNullable(inDto.getType()).ifPresent(model::setType);
		Optional.ofNullable(inDto.getPersonId()).ifPresent(model::setPersonId);
		Optional.ofNullable(inDto.getReportedDate()).ifPresent(model::setReportedDate);
	}

	public static void updateModel(@NotNull Like model, @NotNull LikeInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getAuthorId()).ifPresent(model::setAuthorId);
		Optional.ofNullable(inDto.getPostId()).ifPresent(model::setPostId);
	}

	public static void updateModel(@NotNull Metric model, @NotNull MetricInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getType()).ifPresent(model::setType);
		Optional.ofNullable(inDto.getCalories()).ifPresent(model::setCalories);
		Optional.ofNullable(inDto.getCarbs()).ifPresent(model::setCarbs);
		Optional.ofNullable(inDto.getProtein()).ifPresent(model::setProtein);
		Optional.ofNullable(inDto.getFat()).ifPresent(model::setFat);
		Optional.ofNullable(inDto.getFoodId()).ifPresent(model::setFoodId);
	}

	public static void updateModel(@NotNull Person model, @NotNull PersonInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getName()).ifPresent(model::setName);
		Optional.ofNullable(inDto.getEmail()).ifPresent(model::setEmail);
		Optional.ofNullable(inDto.getDob()).ifPresent(model::setDob);
		Optional.ofNullable(inDto.getGender()).ifPresent(model::setGender);
		Optional.ofNullable(inDto.getActivity()).ifPresent(model::setActivity);
		Optional.ofNullable(inDto.getImagePath()).ifPresent(model::setImagePath);
	}

	public static void updateModel(@NotNull Post model, @NotNull PostInDto inDto) {
		if (inDto == null) return;

		Optional.ofNullable(inDto.getContent()).ifPresent(model::setContent);
		Optional.ofNullable(inDto.getImagePath()).ifPresent(model::setImagePath);
		Optional.ofNullable(inDto.getAuthorId()).ifPresent(model::setAuthorId);
	}

	//Models to dtos

	public static AimDto modelToDto(@NotNull Aim model) {
		if (model == null) return null;

		return AimDto.builder()
				.id(model.getId())
				.targetWeight(model.getTargetWeight())
				.mentioned(model.getMentioned())
				.personDto(model.getPerson() != null ? modelToDto(model.getPerson()) : null)
				.status(model.getStatus())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static BodyReportDto modelToDto(@NotNull BodyReport model) {
		if (model == null) return null;

		return BodyReportDto.builder()
				.id(model.getId())
				.value(model.getValue())
				.type(model.getType())
				.personId(model.getPersonId())
				.loggedTime(model.getLoggedTime())
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static FoodDto modelToDto(@NotNull Food model) {
		if (model == null) return null;

		return FoodDto.builder()
				.id(model.getId())
				.name(model.getName())
				.metrics(model.getMetrics() == null ? null : model.getMetrics().stream()
						.map(ModelDtoUtil::modelToDto).collect(Collectors.toSet()))
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static FoodReportDto modelToDto(@NotNull FoodReport model) {
		if (model == null) return null;

		return FoodReportDto.builder()
				.id(model.getId())
				.food(modelToDto(model.getFood()))
				.amount(model.getAmount())
				.metric(modelToDto(model.getMetric()))
				.type(model.getType())
				.personId(model.getPersonId())
				.reportedDate(model.getReportedDate())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static LikeDto modelToDto(@NotNull Like model) {
		if (model == null) return null;

		return LikeDto.builder()
				.id(model.getId())
				.postId(model.getPostId())
				.authorId(model.getAuthorId())
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static MetricDto modelToDto(@NotNull Metric model) {
		if (model == null) return null;

		return MetricDto.builder()
				.id(model.getId())
				.type(model.getType())
				.calories(model.getCalories())
				.carbs(model.getCarbs())
				.protein(model.getProtein())
				.fat(model.getFat())
				.foodId(model.getFoodId())
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static PersonDto modelToDto(@NotNull Person model) {
		if (model == null) return null;

		return PersonDto.builder()
				.id(model.getId())
				.name(model.getName())
				.email(model.getEmail())
				.dob(model.getDob())
				.gender(model.getGender())
				.activity(model.getActivity())
				.imagePath(model.getImagePath())
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

	public static PostDto modelToDto(@NotNull Post model) {
		if (model == null) return null;

		return PostDto.builder()
				.id(model.getId())
				.content(model.getContent())
				.imagePath(model.getImagePath())
				.likesCount(model.getLikes() != null ? model.getLikes().size() : 0)
				.authorId(model.getAuthorId())
				.createdAt(model.getCreatedAt())
				.modifiedAt(model.getModifiedAt())
				.build();
	}

}
