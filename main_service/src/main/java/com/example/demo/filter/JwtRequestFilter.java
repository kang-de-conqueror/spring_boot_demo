package com.example.demo.filter;

import com.example.demo.constant.Constant;
import com.example.demo.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader(Constant.AUTHORIZATION);

        String username = null;
        String jwt = null;

        if (StringUtils.isNotEmpty(tokenHeader) && tokenHeader.startsWith(Constant.BEARER_)) {
            jwt = tokenHeader.substring(Constant.START_INDEX_JWT);
            try {
                username = jwtService.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException ie) {
                log.error("Unable to get JWT: {}", ie.getMessage());
            } catch (ExpiredJwtException ee) {
                log.error("JWT has expired: {}", ee.getMessage());
            }
        } else {
            logger.warn("JWT is empty");
        }

        if (StringUtils.isNotEmpty(username) && null == SecurityContextHolder.getContext().getAuthentication()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("User: {}, Role: {}", userDetails.getUsername(), userDetails.getAuthorities());
            if (jwtService.validateToken(jwt, username)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
