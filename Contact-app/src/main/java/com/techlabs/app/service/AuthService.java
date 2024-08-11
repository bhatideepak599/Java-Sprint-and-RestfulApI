package com.techlabs.app.service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDTO registerDTO, String newRole);
}
