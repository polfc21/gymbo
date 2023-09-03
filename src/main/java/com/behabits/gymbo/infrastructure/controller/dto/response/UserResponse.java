package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.domain.models.Sport;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Sport> sports;
}
