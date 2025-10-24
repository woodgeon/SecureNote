package com.example.securenote.domain.auth.controller;

import com.example.securenote.domain.auth.dto.LoginRequest;
import com.example.securenote.domain.auth.dto.RegisterRequest;
import com.example.securenote.domain.user.entity.Role;
import com.example.securenote.domain.user.entity.User;
import com.example.securenote.domain.user.repository.UserRepository;
import com.example.securenote.global.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        String email = req.email();
        String rawPassword = req.password();
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("duplicated");
        userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(rawPassword))
                        .role(Role.USER)
                        .build()
        );
        return Map.of("ok", true);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {
        String email = req.email();
        String rawPassword = req.password();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new  IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        return Map.of("accessToken", token, "tokenType", "Bearer");

    }
}
