package com.sparta.blogapi.service;

import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
import com.sparta.blogapi.entity.Blog;
import com.sparta.blogapi.repository.BlogRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogService {

    //데이터 베이스 대신 Map 사용
    private final Map<Long, Blog> postList = new HashMap<>();

    public List<BlogResponseDto> getPosts() {

        // Map To List
        List<BlogResponseDto> responseList = postList.values().stream()
                .map(BlogResponseDto::new).toList();

        return responseList;
    }

    public BlogResponseDto createPost(BlogRequestDto requestDto) {
        //RequestDto -> Entity (데이터 베이스랑 소통하는 Entity class로 변경)
        Blog blog = new Blog(requestDto); //requestDto한테 클라이언트가 보내준 데이터를 값으로 들어옴

        //Blog Post Max ID (Post 중복 X , 구분값)
        Long maxId = postList.size() > 0 ? Collections.max(postList.keySet()) + 1 : 1;
        blog.setId(maxId);

        //DB 저장 (Map)
        BlogRepository blogRepository = new BlogRepository();

        postList.put(blog.getId(), blog);

        //Entity -> ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }


    public Long getSelectPost(Long id, BlogRequestDto requestDto) {

        if (postList.containsKey(id)) {
            //해당 게시글 가져오기
            Blog blog = postList.get(id);
            return id;
        } else {
            //데이터가 없을 경우
            throw new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.");
        }
    }

    public Long updatePost(Long id, BlogRequestDto requestDto) {

        //해당 게시글이 데이터베이스에 존재하는지 확인
        if (postList.containsKey(id)) {
            //해당 게시글 가져오기
            Blog blog = postList.get(id);

            //게시글 수정
            blog.update(requestDto);
            return blog.getId(); // 블로그의 id값 리턴

        } else {
            //데이터가 없을 경우
            throw new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.");
        }
    }

    public Long deletePost(Long id) {

        //해당 게시글이 데이터베이스에 존재하는지 확인
        if (postList.containsKey(id)) {
            //해당 게시글 삭제하기
            postList.remove(id);
            return id;
        } else {
            //데이터가 없을 경우
            throw new IllegalArgumentException("선택한 게시글이 존재하지 않습니다.");
        }
    }
}
