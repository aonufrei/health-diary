package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "aims")
public class Aim {

	@Id
	@GeneratedValue
	private Integer id;
	private float targetWeight;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;
	@CreatedDate
	private LocalDate mentioned;
	private AimStatus status;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
