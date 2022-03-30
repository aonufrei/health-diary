package com.aonufrei.healthdiary.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private int id;
    private String content;
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Person author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Like> likes;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
