package com.doztrk.libraryproject.controller.user;

import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.payload.response.user.UserResponseWithBookAndLoan;
import com.doztrk.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseMessage<UserResponse> register(@RequestBody @Valid UserRequest userRequest, String role) {
        return userService.register(userRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage<UserResponse> getAuthenticatedUser(
            HttpServletRequest httpServletRequest) {
        return userService.getAuthenticatedUser(httpServletRequest);
    }

    @GetMapping("/loans")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public ResponseMessage<Page<UserResponseWithBookAndLoan>> getLoansForAuthenticatedUser(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return userService.getLoansForAuthenticatedUser(httpServletRequest, page, size, sort, type);
    }



}
