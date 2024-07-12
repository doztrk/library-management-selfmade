package com.doztrk.libraryproject.repository.user;

import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleType(RoleType roleType);


    Set<Role> findByRoleTypeEquals(RoleType roleType);

    Long countByRoleType(RoleType roleType);

    @Query(value = "SELECT COUNT(u) FROM Role u WHERE u.roleType = ?1")
    long countAdmin(RoleType roleType);
}
