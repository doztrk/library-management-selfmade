package com.doztrk.libraryproject.repository.user;

import com.doztrk.libraryproject.entity.concretes.user.UserRole;
import com.doztrk.libraryproject.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByRoleType(RoleType roleType);
}
