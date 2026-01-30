package com.ruben.cementerio.controller.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.dto.auth.JwtResponse;
import com.ruben.cementerio.dto.auth.LoginRequest;
import com.ruben.cementerio.security.CustomUserDetails;
import com.ruben.cementerio.security.JwtUtils;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/api/auth/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        //Autenticamos al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        //Guardamos el authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generamos el token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        //Obtenemos los roles
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                                        .stream()
                                        .map(a -> a.getAuthority())
                                        .collect(Collectors.toList());

        // Devolvemos JWTResponse
        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }
}