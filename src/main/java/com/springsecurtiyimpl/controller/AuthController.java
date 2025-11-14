package com.springsecurtiyimpl.controller;

import com.springsecurtiyimpl.config.JwtService;
import com.springsecurtiyimpl.dto.AuthRequest;
import com.springsecurtiyimpl.dto.AuthResponse;
import com.springsecurtiyimpl.dto.RegisterRequest;
import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.repo.UserRepository;
import com.springsecurtiyimpl.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService  jwtService;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailsService customUserDetailsService;


    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        if(userRepository.findByUsername((registerRequest.getUsername())).isPresent()){
            return ResponseEntity.badRequest().body("Username is already in use");
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("ROLE_USER").build();
        userRepository.save(user);
        return ResponseEntity.ok("Registered successfully");
    }

    @RequestMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest  authRequest){
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
        final  User loadUserDetails = (User) customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtService.generateToken(loadUserDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token,loadUserDetails.getUsername(),loadUserDetails.getEmail()));
    }
}
