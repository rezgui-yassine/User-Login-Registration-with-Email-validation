package com.yessinCoding.auth;

import com.yessinCoding.email.EmailService;
import com.yessinCoding.entity.Token;
import com.yessinCoding.entity.User;
import com.yessinCoding.repository.RoleRepository;
import com.yessinCoding.repository.TokenRepository;
import com.yessinCoding.repository.UserRepository;
import com.yessinCoding.role.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public void register(@Valid RegistrationRequest registrationRequest) {
        // Debugging: Check if dependencies are injected
        if (roleRepository == null) {
            throw new IllegalStateException("roleRepository is not injected");
        }
        if (passwordEncoder == null) {
            throw new IllegalStateException("passwordEncoder is not injected");
        }
        if (userRepository == null) {
            throw new IllegalStateException("userRepository is not injected");
        }
        if (tokenRepository == null) {
            throw new IllegalStateException("tokenRepository is not injected");
        }

        // Retrieve the "USER" role from the role repository. If not found, throw an IllegalArgumentException.
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Error: Role is not found"));

        // Create a new User object using the builder pattern.
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        // Send a validation email to the user.
        String newToken = generateAndSaveActivationToken(user);
        // send email

    }

    private String generateAndSaveActivationToken(User user) {
        // generate a new token
        String generatedToken = generateAndSaveActivationCode(6);
        // save the token
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateAndSaveActivationCode(int length) {
        // generate a random code
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // generate a random index 0-9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}