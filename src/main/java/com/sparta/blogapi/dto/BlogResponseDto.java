package com.sparta.blogapi.dto;


import com.sparta.blogapi.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
    public class BlogResponseDto {
    // 게시물 조회 요청에 대한 응답으로 사용
    // id 값과 password값은 X
        private String title;
        private String username;
        private String contents;
        private LocalDateTime createdAt;
        private LocalDateTime mpdifieAt;

        public BlogResponseDto(Blog blog) {
            this.title = blog.getTitle();
            this.username = blog.getUsername();
            this.contents = blog.getContents();
            this.createdAt = blog.getCreatedAt();
            this.mpdifieAt = blog.getModifiedAt();
        }

    }


