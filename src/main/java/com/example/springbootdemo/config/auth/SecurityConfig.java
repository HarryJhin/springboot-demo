package com.example.springbootdemo.config.auth;

import com.example.springbootdemo.domain.user.Role;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final CustomOAuth2UserService CustomOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션 disable
                .and()
                    .authorizeRequests() // URL별 권한 관리 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // antMatchers: 권한 관리 대상을 지정, permitAll(): "/" 등의 지정된 URL 전체 열람 권한 부여
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "/api/v1/**" 주소는 USER 권한을 가진 사람만 접근 가능
                    .anyRequest().authenticated() // anyRequest: 설정된 값을 제외한 나머지 URL, authenticated: 인증된 사용자(로그인한 사용자)만 접근 가능
                .and()
                    .logout() // 로그아웃 관련 설정
                        .logoutSuccessUrl("/") // 로그아웃 성공시 "/" 주소로 이동
                .and()
                    .oauth2Login() // OAuth 2 로그인 관련 설정
                        .userInfoEndpoint() // 로그인 성공 후 사용자 정보 가져올 때의 설정
                            .userService(CustomOAuth2UserService); // 소셜 서비스(구글, 페이스북 등)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 것
    }
}
