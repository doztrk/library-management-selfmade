package com.doztrk.libraryproject;

import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.payload.request.user.UserRequest;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import com.doztrk.libraryproject.service.user.UserRoleService;
import com.doztrk.libraryproject.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryProjectApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LibraryProjectApplication.class);

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
        logger.info("Starting CommandLineRunner");

        try {
            if (userRoleService.getAllUserRole().isEmpty()) {
                logger.info("No roles found, initializing roles...");

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

                if (userRoleService.countAllAdmins() == 0) {
                    logger.info("No admins found, creating default admin...");

                    UserRequest adminRequest = new UserRequest();
                    adminRequest.setEmail("admin@admin.com");
                    adminRequest.setPassword("123456");
                    adminRequest.setFirstName("Doğu");
                    adminRequest.setLastName("Öztürk");
                    adminRequest.setPhone("111-111-1111");
                    adminRequest.setBirthDate(LocalDate.of(1980, 2, 2));
                    adminRequest.setRoleName("Admin");

                    userService.createUser(adminRequest, "Admin");
                    logger.info("Admin user created");
                }
            }
        } catch (Exception e) {
            logger.error("Error in CommandLineRunner", e);
        }

        logger.info("CommandLineRunner finished");
    }
}
