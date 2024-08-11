package com.techlabs.app.config;

import com.techlabs.app.security.JwtAuthenticationEntryPoint;
import com.techlabs.app.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	private JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	static AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorize -> authorize
	            // Swagger and public API access
	            .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
	                             "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
	            .requestMatchers("/api/auth/**").permitAll()

	 
	            .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}/").hasRole("ADMIN")

	         
	            .requestMatchers(HttpMethod.POST, "/api/contacts/").hasRole("STAFF")
	            .requestMatchers(HttpMethod.GET, "/api/contacts/").hasRole("STAFF")
	            .requestMatchers(HttpMethod.GET, "/api/contacts/{id}/").hasRole("STAFF")
	            .requestMatchers(HttpMethod.PUT, "/api/contacts/{id}/**").hasRole("STAFF")
	            .requestMatchers(HttpMethod.DELETE, "/api/contacts/{id}/").hasRole("STAFF")

	            .requestMatchers(HttpMethod.POST, "/api/contact-details/**").hasRole("STAFF")
	            .requestMatchers(HttpMethod.GET, "/api/contact-details/**").hasRole("STAFF")
	            .requestMatchers(HttpMethod.PUT, "/api/contact-details/**").hasRole("STAFF")
	            .requestMatchers(HttpMethod.DELETE, "/api/contact-details/**").hasRole("STAFF")

	            // All other requests require authentication
	            .anyRequest().authenticated())
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}


}
