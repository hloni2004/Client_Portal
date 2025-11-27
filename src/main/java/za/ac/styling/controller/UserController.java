package za.ac.styling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String name,
                                         @RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam UserRole role) {
        User registered = userService.register(name, email, password, role);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email,
                                      @RequestParam String password) {
        User user = userService.login(email, password);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        List<User> users = userService.searchByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<User>> getUsersByCompany(@PathVariable String companyName) {
        List<User> users = userService.findByCompanyName(companyName);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setUserId(id);
        User updated = userService.update(user);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable Integer id,
                                              @RequestParam String name,
                                              @RequestParam(required = false) String companyName,
                                              @RequestParam(required = false) String phone) {
        userService.updateProfile(id, name, companyName, phone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Integer id,
                                                @RequestParam String oldPassword,
                                                @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long count = userService.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
