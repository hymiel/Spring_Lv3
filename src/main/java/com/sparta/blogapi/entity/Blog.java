package com.sparta.blogapi.entity;

import com.sparta.blogapi.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Blog {
        private Long id; // 게시글 고유 id
        private String username; //작성자명
        private String contents; //작성내용

    public Blog(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}
