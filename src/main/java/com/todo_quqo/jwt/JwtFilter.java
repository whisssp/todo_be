package com.todo_quqo.jwt;

import com.todo_quqo.service.UserDetailsExtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_SUFFIX = "Bearer";
    private final UserDetailsExtService userDetailsExtService;
//    private final JwtUtil jwtUtil;

    private final JwtProvider jwtProvider;

    public JwtFilter(UserDetailsExtService userDetailsExtService,
//                     JwtUtil jwtUtil,
                     JwtProvider jwtProvider) {
        this.userDetailsExtService = userDetailsExtService;
//        this.jwtUtil = jwtUtil;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if (!jwtProvider.validateToken(token, request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(BEARER_SUFFIX)) {
            return false;
        }

        return true;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
//        CustomUserDetails userDetails = (CustomUserDetails) getUserDetails(token);

//        UsernamePasswordAuthenticationToken
//                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        authentication.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    private UserDetails getUserDetails(String token) {
//        return userDetailsExtService.loadUserById(jwtUtil.getSubject(token));
//    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return header.split(" ")[1].trim();
    }
}