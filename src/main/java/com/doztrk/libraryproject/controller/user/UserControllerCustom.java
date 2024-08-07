package com.doztrk.libraryproject.controller.user;

import com.doztrk.libraryproject.payload.mappers.UserMapper;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import com.doztrk.libraryproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerCustom {


    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<Page<UserResponse>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return userService.getAllUsers(page, size, sort, type);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest,String userRole) {
        return userService.createUser(userRequest, userRole);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> updateUser(@RequestBody @Valid UserRequest userUpdateRequest, @PathVariable  Long userId, HttpServletRequest httpServletRequest){
        return userService.updateUser(userUpdateRequest,userId,httpServletRequest);
    }


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<UserResponse> deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }




}
