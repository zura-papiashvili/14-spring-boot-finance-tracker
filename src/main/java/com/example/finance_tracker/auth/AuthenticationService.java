package com.example.finance_tracker.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.finance_tracker.config.JwtService;
import com.example.finance_tracker.modules.user.UserRepository;
import com.example.finance_tracker.modules.user.entities.Role;
import com.example.finance_tracker.modules.user.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {

                System.out.println("Registering user: " + request.getEmail());
                System.out.println("Registering user: " + request.getEmail());
                System.out.println("Registering user: " + request.getEmail());
                System.out.println("Registering user: " + request.getEmail());
                var user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();

                userRepository.save(user);

                var jwtToken = jwtService.generateToken(user);

                System.out.println(jwtToken);

                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                System.out.println("hello");
                System.out.println(request.getEmail());
                System.out.println(request.getPassword());
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                System.out.println(1);
                var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
                System.out.println(user);
                var jwtToken = jwtService.generateToken(user);
                System.out.println(jwtToken);

                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

}
