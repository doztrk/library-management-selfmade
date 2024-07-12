package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponseWithBookAndLoan;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private  UserRoleRepository userRoleRepository;

    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAdress())
                .phone(userRequest.getPhone())
                .birthDate(userRequest.getBirthDate())
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

    public User mapUserUpdateRequestToUser(User userToBeUpdated, UserRequest userUpdateRequest) {
        return User.builder()
                .id(userToBeUpdated.getId())
                .firstName(userUpdateRequest.getFirstName())
                .lastName(userUpdateRequest.getLastName())
                .address(userUpdateRequest.getAdress())
                .phone(userUpdateRequest.getPhone())
                .birthDate(userUpdateRequest.getBirthDate())
                .email(userUpdateRequest.getEmail())
                .password(userUpdateRequest.getPassword())
                .build();
    }

    public User mapUpdatedUserToCurrentUser(User currentUser, User toBeUpdatedUser) {
        return User.builder()
                .id(currentUser.getId())
                .firstName(toBeUpdatedUser.getFirstName())
                .lastName(toBeUpdatedUser.getLastName())
                .address(toBeUpdatedUser.getAddress())
                .phone(toBeUpdatedUser.getPhone())
                .birthDate(toBeUpdatedUser.getBirthDate())
                .email(toBeUpdatedUser.getEmail())
                .password(toBeUpdatedUser.getPassword())
                .build();
    }
}
