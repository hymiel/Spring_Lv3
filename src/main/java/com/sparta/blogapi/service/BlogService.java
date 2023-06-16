package com.sparta.blogapi.service;

import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
import com.sparta.blogapi.entity.Blog;
import com.sparta.blogapi.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public List<BlogResponseDto> getPosts() {
        //DB 조회, 내림차순 정렬
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public BlogResponseDto createPost(BlogRequestDto requestDto) {
        //RequestDto -> Entity (데이터 베이스랑 소통하는 Entity class로 변경)
        Blog blog = new Blog(requestDto); //requestDto한테 클라이언트가 보내준 데이터를 값으로 들어옴
        //DB 저장
        Blog saveBlog = blogRepository.save(blog);
        //Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;

        //Blog Post Max ID (Post 중복 X , 구분값)
//        Long maxId = postList.size() > 0 ? Collections.max(postList.keySet()) + 1 : 1;
//        blog.setId(maxId);

    }


    public Long getSelectPost(Long id, BlogRequestDto requestDto) {
        //해당 게시글이 데이터베이스에 존재하는지 확인
        Blog blog = findPost(id);

        //해당 게시글 가져오기
        blog.getPost(requestDto);

        return id;
    }

    @Transactional //트랜잭션 변경 감지
    public Long updatePost(Long id, BlogRequestDto requestDto) {

        //해당 게시글이 데이터베이스에 존재하는지 확인
        Blog blog = findPost(id);
        //게시글 수정
        blog.update(requestDto);
        return id; // 블로그의 id값 리턴
    }

    public Long deletePost(Long id) {
        //해당 게시글이 데이터베이스에 존재하는지 확인
        Blog blog = findPost(id);
        //게시글 삭제
        blogRepository.delete(blog);
        return id;

    }

    private Blog findPost(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }
}
