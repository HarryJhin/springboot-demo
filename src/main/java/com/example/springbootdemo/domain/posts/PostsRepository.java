package com.example.springbootdemo.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {   // DB Layer 접근자, 인터페이스에 JpaRepository<Entity 클래스, PK 타입>을 상속하면 CRUD 메서드가 자동 생성
    
}
