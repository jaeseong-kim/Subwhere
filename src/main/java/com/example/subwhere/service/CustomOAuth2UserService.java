package com.example.subwhere.service;

import com.example.subwhere.config.auth.OAuthAttributes;
import com.example.subwhere.config.auth.SessionUser;
import com.example.subwhere.entity.Member;
import com.example.subwhere.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 구분 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("registrationId = {}", registrationId);

        // 키 값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        log.info("userNameAttributeName = {}", userNameAttributeName);

        // OAuth에서 가져온 속성들
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info("attributes = {}", attributes.getAttributes());

        // DB 최신화
        Member member = saveOrUpdate(attributes);

        // 세션에 사용자 저장
        httpSession.setAttribute("user", new SessionUser(member));


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {

        Member member = memberRepository.findById(attributes.getEmail())
                .map(entity -> entity.update(attributes.getEmail(), attributes.getName()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
