package com.yessinCoding.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    // Add this constructor manually to test if Lombok is the issue
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")

public ResponseEntity<AuthenticationResponse> login(
        @RequestBody @Valid LoginRequest request
    )
    {
        return  ResponseEntity.ok(service
                .login(request));

    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam("token") String token) throws MessagingException {
        service.activateAccount(token);
    }


}