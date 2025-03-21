package com.yessinCoding.auth;

import com.yessinCoding.email.EmailService;
import com.yessinCoding.email.EmailTemplateName;
import com.yessinCoding.entity.Token;
import com.yessinCoding.entity.User;
import com.yessinCoding.repository.RoleRepository;
import com.yessinCoding.repository.TokenRepository;
import com.yessinCoding.repository.UserRepository;
import com.yessinCoding.security.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.application.mailing.frontend.activationUrl}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Invalid token: {}", token);
                    return new RuntimeException("Invalid token");
                });

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            log.error("Token expired: {}", token);
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> {
                    log.error("User not found for token: {}", token);
                    return new UsernameNotFoundException("User not found");
                });

        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now()); // Set the validatedAt field
        tokenRepository.save(savedToken);
        log.info("Account activated successfully for user: {}", user.getEmail());
    }

    private String generateAndSaveActivationToken(User user)  {
        String generatedToken = generateActivationCode(6);

        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    public AuthenticationResponse login(LoginRequest request) {
        // 1: Authenticate the user using the provided email and password
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // 2: Create a claims map to store additional information
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        // 3: Add the user's full name to the claims
        claims.put("fullName", user.fullName());
        // 4: Generate a JWT token with the claims and user details
        var jwtToken = jwtService.generateToken(claims, user);

        // Return the authentication response with the generated token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}