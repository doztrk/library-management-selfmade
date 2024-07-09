package com.doztrk.libraryproject.entity.concretes.user;

import com.doztrk.libraryproject.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "userRoles")
    private Set<User> userSet;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(nullable = false)
    private String roleName;

    @PrePersist
    public void prePersist() {
        if (roleName == null && roleType != null) {
            this.roleName = roleType.getName();
        }
    }

}
