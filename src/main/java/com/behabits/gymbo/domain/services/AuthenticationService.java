package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Token;

public interface AuthenticationService {
    Token login(String username, String password);
}
