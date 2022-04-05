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
@Entity(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", updatable = false, insertable = false)
	private Post post;

	@Column(name = "post_id")
	private Integer postId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", updatable = false, insertable = false)
	private Person author;

	@Column(name = "person_id")
	private Integer authorId;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
