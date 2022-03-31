package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "body_reports")
public class BodyReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private float height;
	private float weight;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable=false)
	private Person person;
	@CreationTimestamp
	private LocalDateTime loggedTime;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
}
