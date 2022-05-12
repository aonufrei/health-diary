package com.aonufrei.healthdiary.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"author"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String content;

	private String imagePath;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "author_id", updatable = false, insertable = false)
	private Person author;

	@Column(name = "author_id")
	private Integer authorId;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Like> likes;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}
