package za.ac.styling.factory;

import za.ac.styling.domain.User;
import za.ac.styling.domain.UserRole;
import za.ac.styling.util.ValidationHelper;

public class UserFactory {

    public static User createUser(String name, String email, String password, UserRole role) {
        if (!ValidationHelper.validateName(name)) {
            throw new IllegalArgumentException("Invalid name: must not be empty and must be less than 100 characters");
        }

        if (!ValidationHelper.validateEmail(email)) {
            throw new IllegalArgumentException("Invalid email: must be a valid email format");
        }

        if (!ValidationHelper.validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password: must be at least 8 characters long");
        }

        if (role == null) {
            throw new IllegalArgumentException("Invalid role: role cannot be null");
        }

        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public static User createUser(String name, String email, String password, UserRole role,
                                  String companyName, String phone) {
        if (!ValidationHelper.validateName(name)) {
            throw new IllegalArgumentException("Invalid name: must not be empty and must be less than 100 characters");
        }

        if (!ValidationHelper.validateEmail(email)) {
            throw new IllegalArgumentException("Invalid email: must be a valid email format");
        }

        if (!ValidationHelper.validatePassword(password)) {
            throw new IllegalArgumentException("Invalid password: must be at least 8 characters long");
        }

        if (role == null) {
            throw new IllegalArgumentException("Invalid role: role cannot be null");
        }

        if (phone != null && !ValidationHelper.validatePhone(phone)) {
            throw new IllegalArgumentException("Invalid phone: must be a valid phone number format");
        }

        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .companyName(companyName)
                .phone(phone)
                .build();
    }

    public static User createAdmin(String name, String email, String password) {
        return createUser(name, email, password, UserRole.ADMIN);
    }

    public static User createClient(String name, String email, String password,
                                    String companyName, String phone) {
        return createUser(name, email, password, UserRole.CLIENT, companyName, phone);
    }
}



