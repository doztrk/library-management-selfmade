package com.doztrk.libraryproject;

import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import com.doztrk.libraryproject.service.user.UserRoleService;
import com.doztrk.libraryproject.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryProjectApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;


    public static void main(String[] args) {
        SpringApplication.run(LibraryProjectApplication.class, args);
    }

    public LibraryProjectApplication(UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {

        if (userRoleService.getAllUserRole().isEmpty()) {
            Role admin = new Role();
            admin.setRoleType(RoleType.ADMIN);
            admin.setRoleName("Admin");
            userRoleRepository.save(admin);

            Role employee = new Role();
            employee.setRoleType(RoleType.EMPLOYEE);
            employee.setRoleName("Employee");
            userRoleRepository.save(employee);

            Role member = new Role();
            member.setRoleType(RoleType.MEMBER);
            member.setRoleName("Member");
            userRoleRepository.save(member);

            if (userRoleService.countAllAdmins()== 0) {

                UserRequest adminRequest = new UserRequest();
                adminRequest.setEmail("admin@admin.com");
                adminRequest.setPassword("123456");
                adminRequest.setFirstName("Doğu");
                adminRequest.setLastName("Öztürk");
                adminRequest.setPhone("111-111-1111");
                adminRequest.setBirthDate(LocalDate.of(1980, 2, 2));

                userService.createUser(adminRequest, "Admin");
            }
        }
    }
}
