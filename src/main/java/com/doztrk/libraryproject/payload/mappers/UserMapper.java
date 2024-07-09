package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponseWithBookAndLoan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .adress(userRequest.getAdress())
                .phone(userRequest.getPhone())
                .birthDate(userRequest.getBirthDay())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public UserResponseWithBookAndLoan mapUserWithBookToUserResponseWithBookAndLoan(User user, LoanResponse loanResponse) {
        return UserResponseWithBookAndLoan.builder()
                .id(user.getId())
                .email(user.getEmail())
                .loanResponse(loanResponse)
                .build();
    }
}
