package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.UserApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.USERS)
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserApiMapper mapper;

    @PostMapping(ApiConstant.REGISTER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        User user = this.userService.createUser(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(user), HttpStatus.CREATED);
    }

    @GetMapping(ApiConstant.KILOMETERS)
    public ResponseEntity<List<UserResponse>> findUsersInKilometersOrderedByDistanceFromPlayerId(@PathVariable Double kilometers) {
        List<UserResponse> users = this.userService.findUsersInKilometersOrderedByDistanceFromLoggedUser(kilometers)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
