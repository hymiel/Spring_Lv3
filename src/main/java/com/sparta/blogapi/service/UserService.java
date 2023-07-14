package com.sparta.blogapi.service;


import com.mysql.cj.ServerVersion;
import com.sparta.blogapi.dto.LoginRequestDto;
import com.sparta.blogapi.dto.SignupRequestDto;
import com.sparta.blogapi.dto.UserResponseDto;
import com.sparta.blogapi.entity.User;
import com.sparta.blogapi.entity.UserRoleEnum;
import com.sparta.blogapi.jwt.JwtUtil;
import com.sparta.blogapi.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Optional;

@Service //Service 사용
@RequiredArgsConstructor //생성자 주입으로 데이터베이스에 대한 생성자를 생성하지 않아도 됨
@RequestMapping("/api")
public class UserService {

    private final UserRepository userRepository; // userRepository 주입
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화 인터페이스
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //    1. 회원 가입 API
    public UserResponseDto signup(SignupRequestDto requestDto,HttpServletResponse res) {
        // username, password를 Client에서 전달받기
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 유저 중복확인
        if (userRepository.findByUsername(username).isPresent()) { // isPresent()는 Optional 객체가 비어있지 않은 경우에 true를 반환하고, 값이 존재하지 않는 경우에 false를 반환
            //중복된 유저네임이 존재 할 경우 예외처리
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호를 다시 확인해주세요.");
            }
            role = UserRoleEnum.ADMIN;
        }
        String authkey = requestDto.getAuthKey();
        User user = new User(username, password, role);
        userRepository.save(user);
        //    - DB에 중복된 username 이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
        UserResponseDto userResponseDto = new UserResponseDto("회원가입이 완료되었습니다.", 200);
        return userResponseDto;
    }


    //    2. 로그인 API
    @Transactional(readOnly = true)
    public UserResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) throws IOException {
        //    - username, password를 Client에서 전달받기
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        //username 확인
        //    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("회원가입 된 사용자가 없습니다."));

        //password 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급
        //JWT Token 생성 및 쿠키에 저장
        String token = jwtUtil.createToken(user.getUsername());

        //Response 객체에 추가,
        jwtUtil.addJwtToCookie(token, res);

        // 로그인 성공
        UserResponseDto userResponseDto = new UserResponseDto("로그인에 성공하였습니다.",HttpServletResponse.SC_OK);
        return userResponseDto;
    }



}
