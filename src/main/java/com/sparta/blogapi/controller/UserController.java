package com.sparta.blogapi.controller;

import com.sparta.blogapi.dto.LoginRequestDto;
import com.sparta.blogapi.dto.SignupRequestDto;
import com.sparta.blogapi.dto.UserResponseDto;
import com.sparta.blogapi.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor  //생성자 주입으로 userService에 대한 생성자를 생성하지 않아도 됨
public class UserController {

    private final UserService userService;


//    1. 회원 가입 API
    @PostMapping("/user/signup")
    public UserResponseDto signup(@RequestBody SignupRequestDto requestDto,HttpServletResponse res){
        return userService.signup(requestDto,res);
    }



//2. 로그인 API
    @PostMapping("/user/login")
    public UserResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) throws IOException {

        return userService.login(requestDto,res);
    }
}
