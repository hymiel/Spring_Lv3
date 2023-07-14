package com.sparta.blogapi.controller;

import com.sparta.blogapi.dto.BlogDeleteDto;
import com.sparta.blogapi.dto.CommentRequestDto;
import com.sparta.blogapi.dto.CommentResponseDto;
import com.sparta.blogapi.security.UserDetailsImpl;
import com.sparta.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.createComment(commentRequestDto, userDetails.getUser()));
    }
    //댓글 수정
    @PutMapping("/comments")
    public ResponseEntity<CommentRequestDto> updateComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto responseDto = commentService.updateComment(requestDto,userDetails.getUser());
            return ResponseEntity.ok().body(requestDto);
        } catch (SecurityException e) {
            throw new SecurityException("수정 권한이 없습니다.");
        }
    }

    //댓글 삭제
    @DeleteMapping("/comments")
    public ResponseEntity<BlogDeleteDto> deleteComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.deleteComment(requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new BlogDeleteDto("댓글이 삭제 완료 되었습니다.",HttpStatus.OK.value()));
        } catch (SecurityException e) {
            return ResponseEntity.badRequest().body(new BlogDeleteDto("댓글 삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
