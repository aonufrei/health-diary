package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "food_reports")
public class FoodReport {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	private Food food;
	private Integer amount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metric_id")
	private Metric metric;
	@CreatedDate
	private LocalDate reportedDate;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
}
