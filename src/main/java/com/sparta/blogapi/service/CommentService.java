package com.sparta.blogapi.service;

import com.sparta.blogapi.dto.CommentRequestDto;
import com.sparta.blogapi.dto.CommentResponseDto;
import com.sparta.blogapi.entity.Blog;
import com.sparta.blogapi.entity.Comment;
import com.sparta.blogapi.entity.User;
import com.sparta.blogapi.entity.UserRoleEnum;
import com.sparta.blogapi.repository.BlogRepository;
import com.sparta.blogapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        //게시글 확인
        Blog blog = blogRepository.findById(requestDto.getPostId()).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment(blog, requestDto.getComment(), user);
        blog.addCommentList(comment);
        log.info("댓글" + comment.getComment() + "등록");
        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto updateComment(CommentRequestDto requestDto, User user) {
        Comment comment = findComment(requestDto.getCommentId());


        if (!user.getRole().equals(UserRoleEnum.ADMIN) && (!comment.getUser().equals(user))) {
            throw new SecurityException("수정 권한이 없습니다.");
        }
        comment.update(requestDto.getComment());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(CommentRequestDto requestDto, User user) {
        Comment comment = findComment(requestDto.getCommentId());

        if (!user.getRole().equals(UserRoleEnum.ADMIN) && (!comment.getUser().equals(user))) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }


    @Transactional
    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 id 입니다."));
    }
}
