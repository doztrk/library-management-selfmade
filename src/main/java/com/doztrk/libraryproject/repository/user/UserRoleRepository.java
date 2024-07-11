package com.doztrk.libraryproject.repository.user;

import com.doztrk.libraryproject.entity.concretes.user.UserRole;
import com.doztrk.libraryproject.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByRoleType(RoleType roleType);


    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);

    Long countByRoleType(RoleType roleType);
}
