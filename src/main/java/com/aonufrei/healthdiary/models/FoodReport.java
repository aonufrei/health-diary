package com.aonufrei.healthdiary.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "food_reports")
public class FoodReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
