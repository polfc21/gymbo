package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApiMapper {

    private final SportApiMapper sportApiMapper;

    public User toDomain(UserRequest request) {
        User domain = new User();
        domain.setUsername(request.getUsername());
        domain.setFirstName(request.getFirstName());
        domain.setLastName(request.getLastName());
        domain.setEmail(request.getEmail());
        domain.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        domain.setSports(this.sportApiMapper.toDomain(request.getSports()));
        return domain;
    }

    public UserResponse toResponse(User domain) {
        UserResponse response = new UserResponse();
        response.setId(domain.getId());
        response.setUsername(domain.getUsername());
        response.setFirstName(domain.getFirstName());
        response.setLastName(domain.getLastName());
        response.setEmail(domain.getEmail());
        response.setSports(domain.getSports());
        return response;
    }

}
