package com.sparta.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
// 게시글 삭제 시 사용 할 Dto
public class BlogDeleteDto {
    private String msg;
    private Integer statusCode;


}

