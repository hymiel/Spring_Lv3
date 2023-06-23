package com.sparta.blogapi.controller;

import com.sparta.blogapi.dto.SignupRequestDto;
import com.sparta.blogapi.dto.UserRequestDto;
import com.sparta.blogapi.dto.UserResponseDto;
import com.sparta.blogapi.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor  //생성자 주입으로 userService에 대한 생성자를 생성하지 않아도 됨
public class UserController {

    private final UserService userService;


//    1. 회원 가입 API
    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }



//2. 로그인 API


    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        return UserService.login();
    }
}
