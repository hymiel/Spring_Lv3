package com.sparta.blogapi.service;

import com.sparta.blogapi.dto.BlogDeleteDto;
import com.sparta.blogapi.dto.BlogRequestDto;
import com.sparta.blogapi.dto.BlogResponseDto;
import com.sparta.blogapi.entity.Blog;
import com.sparta.blogapi.jwt.InvalidTokenException;
import com.sparta.blogapi.jwt.JwtUtil;
import com.sparta.blogapi.repository.BlogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입으로 데이터베이스에 대한 생성자를 생성하지 않아도 됨
public class BlogService {

    private final BlogRepository blogRepository; //데이터 베이스
    private final JwtUtil jwtUtil;

//    @Autowired > RequiredArgsConstructor 미 사용 시 직접 생성자 작성
//    public BlogService(JwtUtil jwtUtil, BlogRepository blogRepository) {
//        this.jwtUtil = jwtUtil;
//        this.blogRepository = blogRepository;
//    }

    //전체 게시글 목록 조회 API
    //- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    //- 작성 날짜 기준 내림차순으로 정렬하기
    public List<BlogResponseDto> getPosts() {
        //DB 조회, 내림차순 정렬
        return blogRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(BlogResponseDto::new).toList();
    }

    //게시글 작성 API
    //- 제목, 작성자명, 비밀번호, 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기
    @Transactional
    public BlogResponseDto createPost(BlogRequestDto requestDto) throws InvalidTokenException {
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //토큰 유효성 검사
        String token = jwtUtil.getJwtFromHeader((HttpServletRequest) requestDto); // HTTP 헤더에서 토큰 추출
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }

        //RequestDto -> Entity (데이터 베이스랑 소통하는 Entity class로 변경)
        Blog blog = new Blog(requestDto); //requestDto한테 클라이언트가 보내준 데이터를 값으로 들어옴
        blog.setAuthor(username);

        //DB 저장
        Blog saveBlog = blogRepository.save(blog);

        //Entity -> ResponseDto
        return new BlogResponseDto(blog);
    }

    //선택한 게시글 조회 API
    // - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
    // - (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    @Transactional(readOnly = true)
    public BlogResponseDto getSelectPost(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        return new BlogResponseDto(blog);
    }

    //선택한 게시글 수정 API
    // - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    // - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
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

    //선택한 게시글 삭제 API
    // - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    // - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기
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

    //헤더에서 토큰 추출 - 튜터님 피드백으로 주석처리
//    private String getTokenFromHeader() throws InvalidTokenException {
//        HttpServletRequest request // 현재 요청 정보 받고
//                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String authorizationHeader = request.getHeader("Authorization");
//        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer")) //Authorization -> null X,Bearer로 시작하는 토큰값만 추출
//        {
//            return subStringToken(authorizationHeader); // 토큰 값을 추출
//        }
//        return authorizationHeader;
//    }
}