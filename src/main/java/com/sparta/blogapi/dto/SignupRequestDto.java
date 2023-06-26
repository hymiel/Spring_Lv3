package com.sparta.blogapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class SignupRequestDto { // 회원가입 시 필요한 정보

    //username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.

    //regexp = "^[a-z0-9]*$" : 정규표현식(regular expression) 패턴을 나타내는 문자열
    //^: 문자열의 시작을 나타냅니다.
    //[a-z0-9]: 알파벳 소문자(a부터 z)와 숫자(0부터 9) 중 하나의 문자와 일치합니다.
    // 대괄호([]) 안에 있는 문자 클래스(character class)는 해당 위치에 어떤 문자가 올 수 있는지를 나타냅니다.
    //*: 앞에 있는 패턴이 0번 이상 반복될 수 있음을 나타냅니다.
    // $: 문자열의 끝을 나타냅니다.
    @NotBlank
    @Size(min = 4, max = 10, message = "4자 이상, 10자 이하")
    @Pattern(regexp = "^[a-z0-9]*$",message = "알파벳 소문자(a~z), 숫자(0~9)")
    private String username;


    //    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.
    @NotBlank
    @Size(min = 8, max = 15, message = "8자 이상, 15자 이하")
    @Pattern(regexp = "^[a-z0-9]*$",message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)")
    private String password;

}
