package com.behabits.gymbo.domain.models;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Sport> sports;
    private List<Link> links;
}
