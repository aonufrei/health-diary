package com.aonufrei.healthdiary.models;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "food_reports")
public class FoodReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id", updatable = false, insertable = false)
	private Food food;

	@Column(name = "food_id")
	private Integer foodId;

	private Integer amount;

	private FoodReportType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", insertable = false, updatable = false)
	private Person person;

	@Column(name = "person_id")
	private Integer personId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metric_id", updatable = false, insertable = false)
	private Metric metric;

	@Column(name = "metric_id")
	private Integer metricId;

	@CreatedDate
	private LocalDate reportedDate;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
