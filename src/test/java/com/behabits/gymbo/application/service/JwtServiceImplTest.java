package com.behabits.gymbo.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.behabits.gymbo.application.domain.UserDetails;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserServiceImpl userService;

    @Value("${gymbo.jwt.issuer}")
    private String issuer;

    @Value("${gymbo.jwt.secret}")
    private String secret;

    private static MockedStatic<JWT> jwtMockedStatic;
    private static MockedStatic<Algorithm> algorithmMockedStatic;
    private static MockedStatic<Verification> verificationMockedStatic;

    @BeforeAll
    static void setUp() {
        jwtMockedStatic = mockStatic(JWT.class);
        algorithmMockedStatic = mockStatic(Algorithm.class);
        verificationMockedStatic = mockStatic(Verification.class);
    }

    @AfterAll
    static void tearDown() {
        jwtMockedStatic.close();
        algorithmMockedStatic.close();
        verificationMockedStatic.close();
    }

    @Test
    void givenUsernameAndEmailWhenCreateTokenThenReturnToken() {
        String username = "pol";
        String email = "polfarreny@gmail.com";
        String expectedToken = "EXPECTED TOKEN";
        JWTCreator.Builder jwtBuilder = mock(JWTCreator.Builder.class);

        when(JWT.create()).thenReturn(jwtBuilder);
        when(jwtBuilder.withIssuer(this.issuer)).thenReturn(jwtBuilder);
        doReturn(jwtBuilder).when(jwtBuilder).withIssuedAt(any(Date.class));
        doReturn(jwtBuilder).when(jwtBuilder).withNotBefore(any(Date.class));
        doReturn(jwtBuilder).when(jwtBuilder).withExpiresAt(any(Date.class));
        when(jwtBuilder.withClaim("username", username)).thenReturn(jwtBuilder);
        when(jwtBuilder.withClaim("email", email)).thenReturn(jwtBuilder);
        when(jwtBuilder.sign(Algorithm.HMAC256(this.secret))).thenReturn(expectedToken);

        assertThat(this.jwtService.createToken(username, email), is(expectedToken));
    }

    @Test
    void givenValidTokenWhenGetAuthenticationThenReturnUsernamePasswordToken() {
        String validToken = "VALID TOKEN";
        String username = "pol";
        Verification verification = mock(Verification.class);
        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim claim = mock(Claim.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(JWT.require(Algorithm.HMAC256(this.secret))).thenReturn(verification);
        when(verification.withIssuer(this.issuer)).thenReturn(verification);
        when(verification.build()).thenReturn(jwtVerifier);
        when(jwtVerifier.verify(validToken)).thenReturn(decodedJWT);
        when(decodedJWT.getClaim("username")).thenReturn(claim);
        when(claim.asString()).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        assertThat(this.jwtService.getAuthentication(validToken).getPrincipal(), is(username));
    }

    @Test
    void givenInvalidTokenWhenGetAuthenticationThenReturnNull() {
        String invalidToken = "INVALID TOKEN";
        Verification verification = mock(Verification.class);
        JWTVerifier jwtVerifier = mock(JWTVerifier.class);

        when(JWT.require(Algorithm.HMAC256(this.secret))).thenReturn(verification);
        when(verification.withIssuer(this.issuer)).thenReturn(verification);
        when(verification.build()).thenReturn(jwtVerifier);
        when(jwtVerifier.verify(invalidToken)).thenThrow(JWTVerificationException.class);

        assertNull(this.jwtService.getAuthentication(invalidToken));
    }
}
