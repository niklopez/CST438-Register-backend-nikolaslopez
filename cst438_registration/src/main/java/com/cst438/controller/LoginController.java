package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.AccountCredentials;
import com.cst438.service.JwtService;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password())
            );

            // Generate token
            String jwts = jwtService.getToken(authentication.getName());

            // Build response with the generated token
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                    .build();
        } catch (AuthenticationException e) {
            // Handle authentication failure here
            // You might want to log the exception and send an error response to the client
            // Example: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}
