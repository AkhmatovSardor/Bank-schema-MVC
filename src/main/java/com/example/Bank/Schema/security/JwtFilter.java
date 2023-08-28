package com.example.Bank.Schema.security;

import com.example.Bank.Schema.entity.UserSession;
import com.example.Bank.Schema.repository.UserSessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (jwtUtil.isValid(token)) {
                String sub = jwtUtil.getClaim("sub", token, String.class);
                Optional<UserSession> userDto = this.userSessionRepository.findById(sub);
                userDto.ifPresent(userSession -> {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userSession.getDto(),
                                    null,
                                    userSession.getDto().getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
            }
        }
        filterChain.doFilter(request, response);
    }
}