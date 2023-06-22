package com.sparta.blogapi.controller;

import com.sparta.blogapi.dto.BlogDeleteDto;
import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
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
    //- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    //- 작성 날짜 기준 내림차순으로 정렬하기
    @GetMapping("/posts")
    public List<BlogResponseDto> getPosts() {
         return blogService.getPosts();
    }

    //게시글 작성 API
    //- 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기
    @PostMapping("/posts")
    public BlogResponseDto createPost(@RequestBody BlogRequestDto requestDto) {
        return blogService.createPost(requestDto);
    }

    //선택한 게시글 조회 API
    // - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
    // - (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    @GetMapping("/post/{id}")
    public BlogResponseDto getSelectPost(@PathVariable Long id) {
        return blogService.getSelectPost(id);
    }

    //선택한 게시글 수정 API
    // - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    // - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    @PutMapping("/post/{id}")
    public BlogResponseDto updatePost(@PathVariable Long id,@RequestBody BlogRequestDto requestDto) {
        return blogService.updatePost(id, requestDto, requestDto.getPassword());
    }

    //선택한 게시글 삭제 API
    // - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    // - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기
    @DeleteMapping("/post/{id}")
    public BlogDeleteDto deletePost(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.deletePost(id, requestDto.getPassword());
    }
}