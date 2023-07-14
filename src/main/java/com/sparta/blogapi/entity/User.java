package com.sparta.blogapi.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import jakarta.persistence.*; //위의 3개를 동시에 사용할 수 있는 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //Entity Class

// 게터세터 어노테이션
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users") //user 는 예약어기 때문에 사용이 불가!
public class User {    //사용자 정보 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id 값을 따로 할당하지 않고 자동으로 기본키 생성
    private Long id;

    @Column(nullable = false, unique = true)
    //테이블 컬럼에 매핑 (빈값을 허용하지 않음)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false) // 유저 권한
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.role = role;
        this.password = password;

    }
}
