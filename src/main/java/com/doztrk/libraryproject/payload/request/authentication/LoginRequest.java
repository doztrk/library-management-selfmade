package com.doztrk.libraryproject.payload.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min = 10, max = 80, message = "Your email should be between 10 and 80 chars")
    private String email;

    @NotNull(message = "Password must not be empty")
    private String password;
}