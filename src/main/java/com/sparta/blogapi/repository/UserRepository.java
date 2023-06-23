package com.sparta.blogapi.repository;

import com.sparta.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername (String username);
    // 유저 이름 중복 확인


}
