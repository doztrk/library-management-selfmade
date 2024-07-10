package com.doztrk.libraryproject.payload.request.abstracts;


import com.doztrk.libraryproject.entity.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractUserRequest {

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min = 10, max = 80, message = "Your email should be between 10 and 80 chars")
    private String email;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private Date birthDate;

    @NotNull
    @Size(min = 10, max = 100)
    private String adress;

    @NotNull(message = "Please enter your phone number")
    @Size(min = 12, max = 12, message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Please enter a valid phone number in the format 999-999-9999")
    private String phone;

    @NotNull
    private String roleName;




}
