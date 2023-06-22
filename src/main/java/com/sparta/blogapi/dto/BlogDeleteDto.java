package com.sparta.blogapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //파라미터가 없는 기본 생성자 생성
// 게시글 삭제 시 사용 할 Dto
public class BlogDeleteDto {
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

