package com.aonufrei.healthdiary.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "metrics")
public class Metric {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private FoodMetricType type;

	private Float calories;

	private Float carbs;

	private Float protein;

	private Float fat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id", updatable = false, insertable = false)
	private Food food;

	@Column(name = "food_id")
	private Integer foodId;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
