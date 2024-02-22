package com.findar.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.findar.common.JsonUtil;
import com.findar.exception.SpringSecurityException;
import com.findar.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
//@Order(1)
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            UsernamePasswordAuthenticationToken auth = getAuthentication(request);
            if (Objects.isNull(auth) && request.getMethod().equals("OPTIONS")) {
                chain.doFilter(request, response);
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }

    @SneakyThrows
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) return null;


        String userStr = JWTUtil.getSubject(token.substring(TOKEN_PREFIX.length()));
        com.findar.user.model.User user = JsonUtil.fromJson(userStr, User.class);
        boolean isDeactivated = Stream
            .of(user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(), user.isCredentialsNonExpired())
            .anyMatch(BooleanUtils::isFalse);

        if (isDeactivated) throw new SpringSecurityException("Forbidden");


        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
