package com.example.springbootdemo.config.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import com.example.springbootdemo.config.auth.dto.OAuthAttributes;
import com.example.springbootdemo.config.auth.dto.SessionUser;
import com.example.springbootdemo.domain.user.User;
import com.example.springbootdemo.domain.user.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        // registrationId: 현재 로그인 진행 중인 서비스(구글, 네이버 등)를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // userNameAttributeName: OAuth2 로그인 진행 시 키가 되는 필드 값
        // 구글은 "sub"로 기본 코드를 지원하나, 네이버와 카카오는 기본 지원 없음
        // 네이버 로그인과 구글 로그인을 동시에 지원하기 위해 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes: OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // SessionUser: 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
