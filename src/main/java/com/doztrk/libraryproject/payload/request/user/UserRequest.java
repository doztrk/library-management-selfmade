package com.doztrk.libraryproject.payload.request.user;


import com.doztrk.libraryproject.payload.request.abstracts.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {

    private String userName;
}

