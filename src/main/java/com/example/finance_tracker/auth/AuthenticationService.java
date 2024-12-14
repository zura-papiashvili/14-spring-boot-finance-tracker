package com.example.finance_tracker.auth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.finance_tracker.auth.dto.LoginUserDto;
import com.example.finance_tracker.auth.dto.RegisterUserDto;
import com.example.finance_tracker.auth.dto.VerifyUserDto;
import com.example.finance_tracker.common.exception.UserAlreadyExistsException;
import com.example.finance_tracker.config.email.EmailService;
import com.example.finance_tracker.modules.user.UserRepository;
import com.example.finance_tracker.modules.user.entities.Role;
import com.example.finance_tracker.modules.user.entities.User;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final EmailService emailService;
        private final AuthenticationManager authenticationManager;

        public User signup(RegisterUserDto input) {

                // Check if user exists with the provided email
                Boolean userExists = checkIfUserExistsWithEmail(input.getEmail());

                System.out.println("User exists: " + userExists); // This will log the boolean value

                if (userExists) {
                        // Throw an exception if user already exists
                        throw new UserAlreadyExistsException("User already exists with email: " + input.getEmail());
                }
                User user = new User(
                                input.getFirstName(),
                                input.getLastName(),
                                passwordEncoder.encode(input.getPassword()),
                                input.getEmail());

                user.setVerificationCode(generateVerificationCode());
                user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5));
                user.setEnabled(false);
                user.setRole(Role.USER);
                // sendVerificationEmail(user);

                return userRepository.save(user);
        }

        public User authenticate(LoginUserDto input) {
                User user = userRepository.findByEmail(input.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                if (!user.isEnabled()) {
                        throw new RuntimeException("User not verified, please check your email");
                }

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                input.getEmail(),
                                                input.getPassword()));

                return user;
        }

        public void verifyUser(VerifyUserDto input) {
                Optional<User> optionalUser = userRepository.findByVerificationCode(input.getVerificationCode());

                if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                                throw new RuntimeException("Verification code expired");
                        }
                        if (user.getVerificationCode().equals(input.getVerificationCode())) {
                                user.setEnabled(true);
                                user.setVerificationCode(null);
                                user.setVerificationCodeExpiresAt(null);
                                userRepository.save(user);
                        } else {
                                throw new RuntimeException("Invalid verification code");
                        }
                } else {
                        throw new RuntimeException("Invalid verification code");
                }
        }

        public void resendVerificationCode(String email) {
                Optional<User> optionalUser = userRepository.findByEmail(email);
                if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (user.isEnabled()) {
                                throw new RuntimeException("User already verified");
                        }
                        user.setVerificationCode(generateVerificationCode());
                        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5));
                        // sendVerificationEmail(user);
                        userRepository.save(user);
                } else {
                        throw new RuntimeException("User not found");
                }
        }

        public void sendVerificationEmail(User user) {
                String subject = "Verify your account";
                String verificationCode = user.getVerificationCode();
                String htmlMessage = "<html>"
                                + "<body style=\"font-family: Arial, sans-serif;\">"
                                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode
                                + "</p>"
                                + "</div>"
                                + "</div>"
                                + "</body>"
                                + "</html>";

                try {
                        emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
                } catch (MessagingException e) {
                        e.printStackTrace();
                }
        }

        private String generateVerificationCode() {
                Random random = new Random();
                int code = 100000 + random.nextInt(900000);
                return String.valueOf(code);
        }

        public Boolean checkIfUserExistsWithEmail(String email) {
                Optional<User> user = userRepository.findByEmail(email);
                return user.isPresent(); // Returns true if user exists, false if not
        }

}
