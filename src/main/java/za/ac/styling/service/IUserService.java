package za.ac.styling.service;

import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IService<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> searchByName(String name);

    List<User> findByCompanyName(String companyName);

    User login(String email, String password);

    User register(String name, String email, String password, UserRole role);

    void updateProfile(Integer userId, String name, String companyName, String phone);

    void changePassword(Integer userId, String oldPassword, String newPassword);
}
