package com.sparta.blogapi.service;

import com.sparta.blogapi.dto.BlogDeleteDto;
import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
import com.sparta.blogapi.entity.Blog;
import com.sparta.blogapi.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public List<BlogResponseDto> getPosts() {
        //DB 조회, 내림차순 정렬
        return blogRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(BlogResponseDto::new).toList();
    }


    // Post 작성
    @Transactional
    public BlogResponseDto createPost(BlogRequestDto requestDto) {
        //RequestDto -> Entity (데이터 베이스랑 소통하는 Entity class로 변경)
        Blog blog = new Blog(requestDto); //requestDto한테 클라이언트가 보내준 데이터를 값으로 들어옴
        //DB 저장
        blogRepository.save(blog);
        //Entity -> ResponseDto
        return new BlogResponseDto(blog);
    }


    @Transactional
    public BlogResponseDto getSelectPost(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다.")); //

        return new BlogResponseDto(blog);
    }

    //게시글 수정
    @Transactional //트랜잭션 변경 감지
    public BlogResponseDto updatePost(Long id, BlogRequestDto requestDto, String password) {

        //아이디 값을 레포지토리에서 가져온 뒤, 해당하는 데이터가 없을 경우 예외
        Blog blog = blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다."));

        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        //blog 에 저장된 패스워드를 equals 메서드를 이용하여 비교 후 동일하면 if문 실행
        if (blog.getPassword().equals(password)) {
            blog.update(requestDto);
            blogRepository.save(blog);
            return new BlogResponseDto(blog);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }

    @Transactional
    public BlogDeleteDto deletePost(Long id, String password) {
        //아이디 값을 레포지토리에서 가져온 뒤, 해당하는 데이터가 없을 경우 예외
        Blog blog = blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다."));

        //msg 사용을 위한 객체
        BlogDeleteDto blogDeleteDto = new BlogDeleteDto();

        if (blog.getPassword().equals(password)) {
            blogRepository.deleteById(id); // id 삭제
            blogDeleteDto.setMsg("글이 삭제되었습니다.");
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return blogDeleteDto;
    }
}