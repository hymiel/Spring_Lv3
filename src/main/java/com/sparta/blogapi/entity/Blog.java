package com.sparta.blogapi.entity;

import com.sparta.blogapi.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bolgpost")
@NoArgsConstructor //파라미터가 없는 기본 생성자를 만들어줌
public class Blog extends Timestamped{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // 게시글 고유 id
        @Column(name = "title", nullable = false, length = 500)
        private String title; // 제목
        @Column(name = "username", nullable = false)
        private String username; //작성자명
        @Column(name = "contents", nullable = false, length = 500)
        private String contents; //작성내용
        @Column(name = "password", nullable = false,length = 500)
        private String password;


        //setAuthor
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User author;
    public Blog(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();

    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
    public void setAuthor(String username) {
        this.author = new User();
        this.author.setUsername(username);
    }
}
