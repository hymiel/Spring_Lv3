package com.sparta.blogapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // 댓글 고유 아이디
    @Column
    private String comment; // 댓글내용

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="blog_id")
    private Blog blog;

    public Comment(Blog blog, String comment, User user) {
        this.blog = blog;
        this.comment = comment;
        this.username = user.getUsername();
        this.user = user;
    }

    public void update(String comment) {
        setComment(comment);
    }
}
