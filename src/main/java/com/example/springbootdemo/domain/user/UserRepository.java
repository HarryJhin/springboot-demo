package com.example.springbootdemo.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email); // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 판단하는 메서드
}