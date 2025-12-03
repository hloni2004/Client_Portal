package za.ac.styling.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        // Check if admin already exists
        if (userRepository.findByEmail("admin@clienthub.com").isPresent()) {
            log.info("Admin user already exists. Skipping initialization.");
            return;
        }

        // Create default admin user
        User admin = User.builder()
                .name("System Administrator")
                .email("admin@clienthub.com")
                .password("admin123") // In production, this should be hashed
                .role(UserRole.ADMIN)
                .phone("+27 11 123 4567")
                .companyName("Client Hub Portal")
                .build();

        userRepository.save(admin);
        
        log.info("========================================");
        log.info("DEFAULT ADMIN USER CREATED");
        log.info("========================================");
        log.info("Email: admin@clienthub.com");
        log.info("Password: admin123");
        log.info("Role: ADMIN");
        log.info("========================================");
        log.info("PLEASE CHANGE THE PASSWORD AFTER FIRST LOGIN");
        log.info("========================================");
    }
}
