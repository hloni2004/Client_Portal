package za.ac.styling.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.factory.UserFactory;
import za.ac.styling.repository.UserRepository;
import za.ac.styling.service.IUserService;
import za.ac.styling.util.ValidationHelper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User save(User entity) {
        if (entity == null || !ValidationHelper.validateUser(entity)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        return userRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        if (entity == null || !ValidationHelper.validateUser(entity)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        if (!userRepository.existsById(entity.getUserId())) {
            throw new IllegalArgumentException("User not found with id: " + entity.getUserId());
        }
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByCompanyName(String companyName) {
        return userRepository.findByCompanyName(companyName);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        user.login();
        return userRepository.save(user);
    }

    @Override
    public User register(String name, String email, String password, UserRole role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = UserFactory.createUser(name, email, password, role);
        return userRepository.save(user);
    }

    @Override
    public void updateProfile(Integer userId, String name, String companyName, String phone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        if (!ValidationHelper.validateName(name)) {
            throw new IllegalArgumentException("Invalid name: must not be empty and must be less than 100 characters");
        }

        if (phone != null && !ValidationHelper.validatePhone(phone)) {
            throw new IllegalArgumentException("Invalid phone: must be a valid phone number format");
        }
        
        user.updateProfile(name, companyName, phone);
        userRepository.save(user);
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        
        if (!ValidationHelper.validatePassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password: must be at least 8 characters long");
        }
        
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        
        // Generate a temporary password
        String temporaryPassword = "Temp" + System.currentTimeMillis();
        user.setPassword(temporaryPassword);
        userRepository.save(user);
        
        // In a real application, send an email with the reset link
        // For now, we'll just log it
        System.out.println("Password reset for " + email + ". Temporary password: " + temporaryPassword);
    }
}
