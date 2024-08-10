package com.techlabs.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto, String role, MultipartFile file);
}
