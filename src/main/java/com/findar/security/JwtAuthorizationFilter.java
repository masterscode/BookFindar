package com.findar.security;


import com.findar.common.JsonUtil;
import com.findar.exception.SpringSecurityException;
import com.findar.role.model.Permission;
import com.findar.role.model.Role;
import com.findar.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Order(1)
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

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(TOKEN_PREFIX)){
            return null;
        }


        String userStr = JWTUtil.getSubject("");

        User user = JsonUtil.fromJson(userStr, User.class);

        boolean isDeactivated = Stream
            .of(user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(), user.isCredentialsNonExpired())
            .anyMatch(BooleanUtils::isFalse);

        if (isDeactivated) throw new SpringSecurityException("Forbidden");

        Role role = null;//
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Permission permission: role.getPermissions()){
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
