package com.example.springbootdemo.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.springbootdemo.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 나타낸다.
public class Posts extends BaseTimeEntity {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false) // 컬럼 옵션
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author; // 컬럼 어노테이션이 없더라도 모든 필드는 컬럼이 된다.

    @Builder // 빌더 패턴 클래스를 생성, 생성자 상단 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
