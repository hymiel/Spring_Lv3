package com.sparta.blogapi.controller;

import com.sparta.blogapi.dto.BlogDeleteDto;
import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
import com.sparta.blogapi.jwt.InvalidTokenException;
import com.sparta.blogapi.repository.BlogRepository;
import com.sparta.blogapi.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;


    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    //전체 게시글 목록 조회 API

    @GetMapping("/posts")
    public List<BlogResponseDto> getPosts() {
         return blogService.getPosts();
    }

    //게시글 작성 API
    @PostMapping("/posts")
    public BlogResponseDto createPost(@RequestBody BlogRequestDto requestDto)throws InvalidTokenException{
        return blogService.createPost(requestDto);
    }

    //선택한 게시글 조회 API
    @GetMapping("/post/{id}")
    public BlogResponseDto getSelectPost(@PathVariable Long id) {
        return blogService.getSelectPost(id);
    }


    // 튜터님 피드백으로 코드 수정 : equestDto 를 넘겨주는데 그안에있는 비밀번호를 또 넘겨주고 있는게 중복
    // 기존코드
//    @PutMapping("/post/{id}")
//    public BlogResponseDto updatePost(@PathVariable Long id,@RequestBody BlogRequestDto requestDto) {
//        return blogService.updatePost(id, requestDto, requestDto.getPassword());
//    }
    //선택한 게시글 삭제 API
//    @DeleteMapping("/post/{id}")
//    public BlogDeleteDto deletePost(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.deletePost(id, requestDto.getPassword());
//    }

    //수정코드 : 비밀번호 관련 중복코드 삭제
    //선택한 게시글 수정 API
    @PutMapping("/post/{id}")
    public BlogResponseDto updatePost(@PathVariable Long id,@RequestBody BlogRequestDto requestDto) {
        return blogService.updatePost(id, requestDto);
    }

    //선택한 게시글 삭제 API
    @DeleteMapping("/post/{id}")
    public BlogDeleteDto deletePost(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.deletePost(id, requestDto);
    }
}