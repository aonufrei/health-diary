package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity(name = "food")
public class Food {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
	private Set<Metric> metrics;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
