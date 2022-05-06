package com.aonufrei.healthdiary.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "food")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
	private Set<Metric> metrics;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Food food = (Food) o;
		return Objects.equals(id, food.id) && Objects.equals(name, food.name) && Objects.equals(metrics, food.metrics)
				&& Objects.equals(createdAt, food.createdAt) && Objects.equals(modifiedAt, food.modifiedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, createdAt, modifiedAt);
	}
}
