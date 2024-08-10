package com.techlabs.app.controller;

import com.techlabs.app.dto.JWTAuthResponse;
import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	public AuthController(AuthService authService) {

		this.authService = authService;
	}

//	@Operation(summary = "User Login")
//	@PostMapping(value = { "/login", "/signin" })
//	public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
//		// System.out.println("enter");
//		String token = authService.login(loginDto);
//		// System.out.println(loginDto);
//		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//		jwtAuthResponse.setAccessToken(token);
//
//		return ResponseEntity.ok(jwtAuthResponse);
//	}

//    @Operation(summary = "Register User/Customer")
//    @PostMapping(value = {"/register", "/signup"})
//    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto,@RequestParam(name="role") String name ){
//    	
//    	//System.out.println(registerDto);
//    	String role="ROLE_"+name.toUpperCase();
//    	
//    	
//        String response = authService.register(registerDto,role);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//   
	@Operation(summary = "User login")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginDto loginDTO) {
        logger.info("User login with username: {}", loginDTO.getUsername());
        System.out.println(loginDTO.getUsername());
        String token = authService.login(loginDTO);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(summary = "User registration",
            requestBody = @RequestBody(
                    description = "Registration details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterDto.class))
            ),
            parameters = {
                    @Parameter(name = "role", description = "User role (ADMIN or CUSTOMER)", required = true),
                    @Parameter(name = "file", description = "Profile picture or document", required = true,
                            content = @Content(mediaType = "multipart/form-data"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration successful"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    @PostMapping(value = {"/register", "/signup"}, consumes = {"multipart/form-data"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDTO,
                                           @RequestParam(name = "role") String tempRole,
                                           @RequestParam("file") MultipartFile file) {
        String role = "ROLE_" + tempRole.toUpperCase();
        logger.info("User registration with role: {}", role);
        String response = authService.register(registerDTO, role,file);
        return ResponseEntity.ok(response);
    }

}
