package com.techlabs.app.controller;

import com.techlabs.app.dto.JWTAuthResponse;
import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Login Here")
    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        //System.out.println(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(summary = "Register for New Staff or Admin")
    @PostMapping(value = {"/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO,
                                           @RequestParam(name = "role") String role) {
        String newRole = "ROLE_" + role.toUpperCase();
        String response = authService.register(registerDTO,newRole);

        return ResponseEntity.ok(response);
    }
}
