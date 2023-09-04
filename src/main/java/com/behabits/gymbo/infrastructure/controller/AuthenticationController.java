package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.services.AuthenticationService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LoginRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.AUTH)
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(ApiConstant.LOGIN)
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        Token token = this.authenticationService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }

}
