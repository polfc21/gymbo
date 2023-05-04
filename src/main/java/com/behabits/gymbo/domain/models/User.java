package com.behabits.gymbo.domain.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
