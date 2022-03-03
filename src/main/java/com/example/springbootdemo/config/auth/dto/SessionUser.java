package com.example.springbootdemo.config.auth.dto;

import lombok.Getter;

import java.io.Serializable;

import com.example.springbootdemo.domain.user.User;

@Getter
public class SessionUser implements Serializable { // 인증된 사용자 정보
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
