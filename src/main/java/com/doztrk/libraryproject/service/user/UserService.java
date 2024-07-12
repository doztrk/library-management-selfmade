package com.doztrk.libraryproject.service.user;

import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.UserMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponseWithBookAndLoan;
import com.doztrk.libraryproject.repository.business.LoanRepository;
import com.doztrk.libraryproject.repository.user.UserRepository;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import com.doztrk.libraryproject.service.business.LoanService;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import com.doztrk.libraryproject.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;
    private final PageableHelper pageableHelper;
    private final LoanRepository loanRepository;
    private final LoanService loanService;
    private final UserRoleService userRoleService;


    public ResponseMessage<UserResponse> register(UserRequest userRequest) {
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);

        Set<Role> roles = new HashSet<>();
        Role memberRole = userRoleRepository
                .findByRoleType(RoleType.MEMBER)
                .orElseThrow(
                        () -> new RuntimeException("Member role not found"));
        roles.add(memberRole);
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_REGISTERED)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(user))
                .build();
    }

    public ResponseMessage<UserResponse> getAuthenticatedUser(HttpServletRequest httpServletRequest) {

        String userInfo = (String) httpServletRequest.getAttribute("username");
        User user = userRepository
                .findByEmail(userInfo)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL, userInfo)));

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(userMapper.mapUserToUserResponse(user))
                .build();
    }


    public ResponseMessage<Page<UserResponseWithBookAndLoan>> getLoansForAuthenticatedUser(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {

        String userInfo = (String) httpServletRequest.getAttribute("username");
        User user = userRepository
                .findByEmail(userInfo)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessages.USER_NOT_FOUND_WITH_EMAIL, userInfo)));

        ResponseMessage<Page<LoanResponse>> loanResponse = loanService.getAllLoansForAuthenticatedUser(httpServletRequest, page, size, sort, type);

        Page<UserResponseWithBookAndLoan> userResponsePage = loanResponse
                .getObject()
                .map(loan -> userMapper.mapUserWithBookToUserResponseWithBookAndLoan(user, loan));

        return ResponseMessage.<Page<UserResponseWithBookAndLoan>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(userResponsePage)
                .build();
    }

    public ResponseMessage<Page<UserResponse>> getAllUsers(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        Page<User> userPage = userRepository.findAll(pageable);

        return ResponseMessage.<Page<UserResponse>>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(userPage.map(userMapper::mapUserToUserResponse))
                .build();
    }

    public ResponseMessage<UserResponse> getUserById(Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessages.USER_NOT_FOUND_WITH_ID, userId)));

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(user))
                .build();
    }

    public ResponseMessage<UserResponse> createUser(UserRequest userRequest, String userRole) {


        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhone());

        User user = userMapper.mapUserRequestToUser(userRequest);

        if (userRole.equalsIgnoreCase(RoleType.ADMIN.name())) {
            if (Objects.equals(userRequest.getRoleName(), "Admin")) {
                user.setBuiltIn(true);
            }
            user.setRoles(userRoleService.getUserRole(RoleType.ADMIN));
        } else if (userRole.equalsIgnoreCase(RoleType.MEMBER.getName())) {
            user.setBuiltIn(false);
            user.setRoles(userRoleService.getUserRole(RoleType.MEMBER));
        } else if (userRole.equalsIgnoreCase(RoleType.EMPLOYEE.getName())) {
            user.setBuiltIn(false);
            user.setRoles(userRoleService.getUserRole(RoleType.EMPLOYEE));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

       User savedUser =  userRepository.save(user);


        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }


    public ResponseMessage<UserResponse> updateUser(UserRequest userUpdateRequest, Long userId, HttpServletRequest httpServletRequest) {

        String authentication = (String) httpServletRequest.getAttribute("username");
        User currentUser = userRepository
                .findByEmail(authentication)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, authentication)));

        User userToBeUpdated = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(ErrorMessages.USER_NOT_FOUND_WITH_ID, userId)));

        uniquePropertyValidator.checkDuplicate(userUpdateRequest.getEmail(), userUpdateRequest.getPhone());

        User updatedUser;

        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.ADMIN));
        boolean isEmployee = currentUser.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.EMPLOYEE));
        boolean isMember = userToBeUpdated.getRoles().stream().anyMatch(role -> role.getRoleType().equals(RoleType.MEMBER));

        if (isAdmin || (isEmployee && isMember)) {
            User toBeUpdatedUser = userMapper.mapUserUpdateRequestToUser(userToBeUpdated, userUpdateRequest);
            updatedUser = userMapper.mapUpdatedUserToCurrentUser(currentUser, toBeUpdatedUser);
        } else {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(updatedUser))
                .build();
    }


    public ResponseMessage<UserResponse> deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.USER_NOT_FOUND_WITH_ID));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }

        if (!loanRepository.findByUserId(userId).isEmpty()) {
            throw new BadRequestException(String.format(ErrorMessages.USER_HAS_LOAN, userId));
        }

        userRepository.delete(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(user))
                .build();
    }


}