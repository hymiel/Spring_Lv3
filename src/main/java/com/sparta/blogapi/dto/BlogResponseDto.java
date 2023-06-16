package com.sparta.blogapi.dto;


import com.sparta.blogapi.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
    public class BlogResponseDto {
        private Long id;
        private String title;
        private String username;
        private String contents;
        private LocalDateTime createdAt;
        private LocalDateTime mpdifieAt;

        public BlogResponseDto(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.username = blog.getUsername();
            this.contents = blog.getContents();
            this.createdAt = blog.getCreatedAt();
            this.mpdifieAt = blog.getModifiedAt();
        }

    }


