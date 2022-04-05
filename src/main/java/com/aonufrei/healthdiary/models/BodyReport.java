package com.aonufrei.healthdiary.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "body_reports")
public class BodyReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Float height;

	private Float weight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", insertable = false, updatable = false)
	private Person person;

	@Column(name = "person_id")
	private Integer personId;

	private LocalDateTime loggedTime;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
