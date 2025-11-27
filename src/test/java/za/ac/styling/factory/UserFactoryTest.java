package za.ac.styling.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.util.ValidationHelper;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserFactoryTest {

    @Test
    @Order(1)
    void createUser_With_CorrectData() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        UserRole role = UserRole.ADMIN;


        User user = UserFactory.createUser(name, email, password, role);


        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertNull(user.getCompanyName());
        assertNull(user.getPhone());
        System.out.print(user.toString());
    }

    @Test
    @Order(2)
    void createUser_WithInvalidName() {

        String invalidName = "";
        String email = "john.doe@example.com";
        String password = "password123";
        UserRole role = UserRole.ADMIN;


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser(invalidName, email, password, role);
        });
        assertTrue(exception.getMessage().contains("Invalid name"));
        System.out.print(exception.getMessage());
    }

    @Test
    @Order(3)
    void createUser_WithInvalidEmailn() {
        // Arrange
        String name = "John Doe";
        String invalidEmail = "invalid-email";
        String password = "password123";
        UserRole role = UserRole.ADMIN;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser(name, invalidEmail, password, role);
        });
        assertTrue(exception.getMessage().contains("Invalid email"));
    }
    @Order(4)
    @Test
    void createUser_WithShortPassword_ShouldThrowException() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String shortPassword = "pass";
        UserRole role = UserRole.ADMIN;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser(name, email, shortPassword, role);
        });
        assertTrue(exception.getMessage().contains("Invalid password"));
    }
    @Order(5)
    @Test
    void createUser_WithNullRole_ShouldThrowException() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        UserRole role = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser(name, email, password, role);
        });
        assertTrue(exception.getMessage().contains("Invalid role"));
    }
    @Order(6)
    @Test
    void testCreateUser_WithAllFields() {
        // Arrange
        String name = "Jane Smith";
        String email = "jane.smith@example.com";
        String password = "password123";
        UserRole role = UserRole.CLIENT;
        String companyName = "ABC Corp";
        String phone = "+27123456789";

        // Act
        User user = UserFactory.createUser(name, email, password, role, companyName, phone);

        // Assert
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(companyName, user.getCompanyName());
        assertEquals(phone, user.getPhone());
    }
    @Order(7)
    @Test
    void testCreateUser_WithInvalidPhone() {
        // Arrange
        String name = "Jane Smith";
        String email = "jane.smith@example.com";
        String password = "password123";
        UserRole role = UserRole.CLIENT;
        String companyName = "ABC Corp";
        String invalidPhone = "invalid-phone";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser(name, email, password, role, companyName, invalidPhone);
        });
        assertTrue(exception.getMessage().contains("Invalid phone"));
    }
    @Order(8)
    @Test
    void testCreateUser_WithNullPhone() {
        // Arrange
        String name = "Jane Smith";
        String email = "jane.smith@example.com";
        String password = "password123";
        UserRole role = UserRole.CLIENT;
        String companyName = "ABC Corp";
        String phone = null;

        // Act
        User user = UserFactory.createUser(name, email, password, role, companyName, phone);

        // Assert
        assertNotNull(user);
        assertNull(user.getPhone());
    }
    @Order(9)
    @Test
    void createAdmin_WithValidData() {
        // Arrange
        String name = "Admin User";
        String email = "admin@example.com";
        String password = "adminpass123";

        // Act
        User admin = UserFactory.createAdmin(name, email, password);

        // Assert
        assertNotNull(admin);
        assertEquals(name, admin.getName());
        assertEquals(email, admin.getEmail());
        assertEquals(password, admin.getPassword());
        assertEquals(UserRole.ADMIN, admin.getRole());
    }
    @Order(10)
    @Test
    void createAdmin_WithInvalidData() {
        // Arrange
        String name = "Admin User";
        String invalidEmail = "not-an-email";
        String password = "adminpass123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createAdmin(name, invalidEmail, password);
        });
    }
    @Order(11)
    @Test
    void createClient_WithValidData() {
        // Arrange
        String name = "Client User";
        String email = "client@example.com";
        String password = "clientpass123";
        String companyName = "XYZ Ltd";
        String phone = "+27987654321";

        // Act
        User client = UserFactory.createClient(name, email, password, companyName, phone);

        // Assert
        assertNotNull(client);
        assertEquals(name, client.getName());
        assertEquals(email, client.getEmail());
        assertEquals(password, client.getPassword());
        assertEquals(UserRole.CLIENT, client.getRole());
        assertEquals(companyName, client.getCompanyName());
        assertEquals(phone, client.getPhone());
    }
    @Order(12)
    @Test
    void createClient_WithInvalidPhone() {
        // Arrange
        String name = "Client User";
        String email = "client@example.com";
        String password = "clientpass123";
        String companyName = "XYZ Ltd";
        String invalidPhone = "abc123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createClient(name, email, password, companyName, invalidPhone);
        });
    }
    @Order(13)
    @Test
    void validateUser_WithValidUser() {
        User user = UserFactory.createUser("John Doe", "john@example.com", "password123", UserRole.ADMIN);

        boolean isValid = ValidationHelper.validateUser(user);

        assertTrue(isValid);
    }
    @Order(14)
    @Test
    void validateUser_WithNullUser() {
        User user = null;

        boolean isValid = ValidationHelper.validateUser(user);

        assertFalse(isValid);
    }
    @Order(15)
    @Test
    void validateUser_WithInvalidUser() {
        User user = User.builder()
                .name("")
                .email("invalid-email")
                .password("short")
                .role(UserRole.ADMIN)
                .build();

        boolean isValid = ValidationHelper.validateUser(user);

        assertFalse(isValid);
    }
}