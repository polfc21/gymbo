package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthenticationService;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LoginRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TokenResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.UserApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.AUTH)
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserApiMapper mapper;

    @PostMapping(ApiConstant.REGISTER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        User user = this.userService.createUser(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(user), HttpStatus.CREATED);
    }

    @PostMapping(ApiConstant.LOGIN)
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        Token token = this.authenticationService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }

}
