package com.sparta.blogapi.dto;


import com.sparta.blogapi.entity.Blog;
import lombok.Getter;

    @Getter
    public class BlogResponseDto {
        private Long id;
        private String username;
        private String contents;

        public BlogResponseDto(Blog blog) {
            this.id = blog.getId();
            this.username = blog.getUsername();
            this.contents = blog.getContents();
        }
    }


