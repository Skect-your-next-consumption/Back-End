package com.hana.api.user.service;

import com.hana.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        // 로그인 ID로 User 찾기
        com.hana.api.user.entity.User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Consultant not found"));

        // UserDetails 객체로 반환
        return new User(user.getUserId(), user.getUserPwd(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getValue())));
    }
}
