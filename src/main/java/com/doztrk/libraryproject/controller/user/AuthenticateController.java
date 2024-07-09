package com.doztrk.libraryproject.controller.user;


import com.doztrk.libraryproject.payload.request.authentication.LoginRequest;
import com.doztrk.libraryproject.payload.response.authentication.AuthResponse;
import com.doztrk.libraryproject.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }


}
