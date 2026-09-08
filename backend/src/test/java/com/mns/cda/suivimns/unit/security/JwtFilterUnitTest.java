package com.mns.cda.suivimns.unit.security;

import com.mns.cda.suivimns.security.JwtFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

// Ces tests couvrent la correction: un header Authorization invalide/perime ne doit plus
// faire remonter d'exception a travers le filtre (ce qui cassait meme les routes publiques
// comme /log-in, le filtre tournant sur toutes les routes), mais laisser la requete continuer
// non authentifiee.
@ExtendWith(MockitoExtension.class)
class JwtFilterUnitTest {

    private static final String SECRET = "test-jwt-secret";
    private static final String EMAIL = "user@test.fr";

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        jwtFilter = new JwtFilter(userDetailsService);
        ReflectionTestUtils.setField(jwtFilter, "jwtSecret", SECRET);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private String validToken() {
        return Jwts.builder()
                .setSubject(EMAIL)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    @Test
    void shouldAuthenticateWhenTokenValid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken());

        UserDetails userDetails = mock(UserDetails.class);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_TECHNICIAN"));
        when(userDetails.getAuthorities()).thenAnswer(invocation -> authorities);
        when(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(userDetails);

        jwtFilter.doFilter(request, response, filterChain);

        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldContinueUnauthenticatedWhenTokenExpired() throws Exception {
        String expired = Jwts.builder()
                .setSubject(EMAIL)
                .setExpiration(new Date(System.currentTimeMillis() - 60_000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        when(request.getHeader("Authorization")).thenReturn("Bearer " + expired);
        when(request.getRequestURI()).thenReturn("/log-in");

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void shouldContinueUnauthenticatedWhenTokenSignedWithWrongSecret() throws Exception {
        String wrongSecretToken = Jwts.builder()
                .setSubject(EMAIL)
                .signWith(SignatureAlgorithm.HS256, "some-other-secret")
                .compact();
        when(request.getHeader("Authorization")).thenReturn("Bearer " + wrongSecretToken);
        when(request.getRequestURI()).thenReturn("/log-in");

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void shouldContinueUnauthenticatedWhenTokenMalformed() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer not-a-jwt-token");
        when(request.getRequestURI()).thenReturn("/log-in");

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void shouldContinueUnauthenticatedWhenTokenSubjectUnknown() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken());
        when(request.getRequestURI()).thenReturn("/log-in");
        when(userDetailsService.loadUserByUsername(EMAIL))
                .thenThrow(new UsernameNotFoundException(EMAIL));

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldContinueUnauthenticatedWhenNoAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void shouldContinueUnauthenticatedWhenHeaderHasNoBearerPrefix() throws Exception {
        // Header trop court pour un substring(7) naif: doit etre ignore sans exception.
        when(request.getHeader("Authorization")).thenReturn("short");

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }
}
