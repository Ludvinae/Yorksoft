package com.mns.cda.suivimns.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    protected final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null) {
            String jwt = token.substring(7);

            String email = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            // verification du role de la personne connectée
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authorities : " + auth.getAuthorities());
            System.out.println("Principal : " + auth.getPrincipal());
            System.out.println("Authenticated : " + auth.isAuthenticated());
        }

        filterChain.doFilter(request, response);
    }
}
