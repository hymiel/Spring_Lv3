package com.sparta.blogapi.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
//@MappedSuperclass
public abstract class Timestamped {
    private LocalDateTime createdAt; //작성날짜
}
