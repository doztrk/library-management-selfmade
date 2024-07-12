package com.doztrk.libraryproject.service.user;

import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;


    public Set<Role> getUserRole(RoleType roleType) {
        return userRoleRepository.findByRoleTypeEquals(roleType);
    }

    public List<Role> getAllUserRole() {
        return userRoleRepository.findAll();
    }

    public long countAllAdmins() {
        return userRoleRepository.countAdmin(RoleType.ADMIN);
    }
}
