package com.learningSpringvoot.TasksMgmt.auth;

import com.learningSpringvoot.TasksMgmt.config.JwtService;
import com.learningSpringvoot.TasksMgmt.model.Role;
import com.learningSpringvoot.TasksMgmt.model.User;
import com.learningSpringvoot.TasksMgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.learningSpringvoot.TasksMgmt.config.JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())  // Added email
                .firstName(request.getFirstName())  // Added firstname
                .lastName(request.getLastName())  // Added lastname
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate (AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));


        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
