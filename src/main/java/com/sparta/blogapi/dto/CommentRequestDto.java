package com.sparta.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // 생성시엔 넣지 않아도 자동으로 db에 c_id 가 부여
public class CommentRequestDto {
    private Long postId;
    private Long commentId;
    private String comment;

}
