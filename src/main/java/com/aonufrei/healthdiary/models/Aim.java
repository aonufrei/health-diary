package com.aonufrei.healthdiary.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"person"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "aims")
public class Aim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Float targetWeight;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", updatable = false, insertable = false)
	private Person person;

	@Column(name = "person_id")
	private Integer personId;

	@CreationTimestamp
	private LocalDate mentioned;

	private AimStatus status;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
