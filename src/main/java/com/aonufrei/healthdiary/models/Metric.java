package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity(name = "metrics")
public class Metric {

	@Id
	@GeneratedValue
	private int id;
	private FoodMetricType name;
	private Integer value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="food_id")
	private Food food;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
}
