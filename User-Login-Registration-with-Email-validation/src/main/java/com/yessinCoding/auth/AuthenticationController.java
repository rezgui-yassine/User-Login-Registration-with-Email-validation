package com.yessinCoding.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "This controller is responsible for authenticating the user")

public class AuthenticationController {
    private  final AuthenticationService authenticationService;

    // register user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?>register (
            @RequestBody @Valid RegistrationRequest registrationRequest
    ){
        authenticationService.register(registrationRequest);
        return  ResponseEntity.accepted().build();
    }

    // login user

    // verify user

    // forgot password
    // reset password
    // change password
    // logout
    // refresh token
    // get user profile

}
