package za.ac.styling.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.factory.UserFactory;
import za.ac.styling.service.IUserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    private static User testAdmin;
    private static User testClient;

    @BeforeAll
    static void setUp() {
        testAdmin = UserFactory.createAdmin("Admin User", "admin@example.com", "adminpass123");
        testClient = UserFactory.createClient("Client User", "client@example.com", "clientpass123", "ABC Corp", "+27123456789");
    }

    @Test
    @Order(1)
    void save_WithValidAdmin() {
        User saved = userService.save(testAdmin);
        assertNotNull(saved);
        assertNotNull(saved.getUserId());
        assertEquals("Admin User", saved.getName());
        assertEquals(UserRole.ADMIN, saved.getRole());
        System.out.println("Saved Admin: " + saved);
        
        testAdmin = saved;
    }

    @Test
    @Order(2)
    void save_WithValidClient() {
        User saved = userService.save(testClient);
        assertNotNull(saved);
        assertNotNull(saved.getUserId());
        assertEquals("Client User", saved.getName());
        assertEquals(UserRole.CLIENT, saved.getRole());
        assertEquals("ABC Corp", saved.getCompanyName());
        assertEquals("+27123456789", saved.getPhone());
        System.out.println("Saved Client: " + saved);
        
        testClient = saved;
    }

    @Test
    @Order(3)
    void findById_WhenUserExists() {
        assertNotNull(testAdmin);
        assertNotNull(testAdmin.getUserId(), "User ID should not be null after save");
        
        Optional<User> found = userService.findById(testAdmin.getUserId());
        assertTrue(found.isPresent());
        assertEquals(testAdmin.getUserId(), found.get().getUserId());
        assertEquals("Admin User", found.get().getName());
    }

    @Test
    @Order(4)
    void findByEmail_WhenUserExists() {
        Optional<User> found = userService.findByEmail("client@example.com");
        assertTrue(found.isPresent());
        assertEquals("Client User", found.get().getName());
    }

    @Test
    @Order(5)
    void existsByEmail_WhenEmailExists() {
        boolean exists = userService.existsByEmail("admin@example.com");
        assertTrue(exists);
    }

    @Test
    @Order(6)
    void findByRole() {
        List<User> admins = userService.findByRole(UserRole.ADMIN);
        assertNotNull(admins);
        assertFalse(admins.isEmpty());
        assertTrue(admins.stream().allMatch(u -> u.getRole() == UserRole.ADMIN));
        System.out.println("Total admins: " + admins.size());
    }

    @Test
    @Order(7)
    void searchByName() {
        List<User> users = userService.searchByName("User");
        assertNotNull(users);
        assertFalse(users.isEmpty());
        System.out.println("Users matching 'User': " + users.size());
    }

    @Test
    @Order(8)
    void findByCompanyName_ShouldReturnUsersFromCompany() {
        List<User> users = userService.findByCompanyName("ABC Corp");
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals("ABC Corp", users.get(0).getCompanyName());
    }

    @Test
    @Order(9)
    void login_WithValidCredentials_ShouldLoginUser() {
        User loggedIn = userService.login("admin@example.com", "adminpass123");
        assertNotNull(loggedIn);
        assertNotNull(loggedIn.getLastLogin());
        assertEquals("Admin User", loggedIn.getName());
    }

    @Test
    @Order(10)
    void register_WithValidData_ShouldRegisterUser() {
        User newUser = userService.register("New User", "newuser@example.com", "password123", UserRole.CLIENT);
        assertNotNull(newUser);
        assertNotNull(newUser.getUserId());
        assertEquals("New User", newUser.getName());
        assertEquals("newuser@example.com", newUser.getEmail());
        System.out.println("Registered new user: " + newUser);
    }

    @Test
    @Order(11)
    void register_WithExistingEmail_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> userService.register("Duplicate", "admin@example.com", "password123", UserRole.CLIENT));
    }

    @Test
    @Order(12)
    void updateProfile_WithValidData_ShouldUpdateProfile() {
        userService.updateProfile(testClient.getUserId(), "Client Updated", "XYZ Ltd", "+27987654321");
        
        Optional<User> updated = userService.findById(testClient.getUserId());
        assertTrue(updated.isPresent());
        assertEquals("Client Updated", updated.get().getName());
        assertEquals("XYZ Ltd", updated.get().getCompanyName());
        assertEquals("+27987654321", updated.get().getPhone());
    }

    @Test
    @Order(13)
    void changePassword_WithValidOldPassword_ShouldChangePassword() {
        userService.changePassword(testAdmin.getUserId(), "adminpass123", "newadminpass123");
        
        User loggedIn = userService.login("admin@example.com", "newadminpass123");
        assertNotNull(loggedIn);
        assertEquals("Admin User", loggedIn.getName());
    }

    @Test
    @Order(14)
    void update_WithValidUser_ShouldUpdateUser() {
        User toUpdate = testClient;
        toUpdate.setName("Client Final Update");
        
        User updated = userService.update(toUpdate);
        assertNotNull(updated);
        assertEquals("Client Final Update", updated.getName());
    }

    @Test
    @Order(15)
    void findAll_ShouldReturnAllUsers() {
        List<User> all = userService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.size() >= 3);
        System.out.println("Total users in database: " + all.size());
    }

    @Test
    @Order(16)
    void count_ShouldReturnUserCount() {
        long count = userService.count();
        assertTrue(count >= 3);
        System.out.println("User count: " + count);
    }

    @Test
    @Order(17)
    void existsById_WhenUserExists_ShouldReturnTrue() {
        boolean exists = userService.existsById(testAdmin.getUserId());
        assertTrue(exists);
    }

    @Test
    @Order(18)
    void deleteById_WhenUserExists_ShouldDeleteUser() {
        Optional<User> newUser = userService.findByEmail("newuser@example.com");
        assertTrue(newUser.isPresent());
        
        userService.deleteById(newUser.get().getUserId());
        
        Optional<User> deleted = userService.findById(newUser.get().getUserId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @Order(19)
    void login_WithInvalidPassword_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> userService.login("admin@example.com", "wrongpassword"));
    }

    @Test
    @Order(20)
    void updateProfile_WithInvalidName_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> userService.updateProfile(testAdmin.getUserId(), "", "Company", "+27123456789"));
    }
}