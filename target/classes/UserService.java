package src.services;

import src.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, String> userDatabase;

    public UserService() {
        // Mock database of users
        userDatabase = new HashMap<>();
        userDatabase.put("admin", "admin123"); // Default admin
        userDatabase.put("cashier", "cashier123"); // Default cashier
    }

    public boolean authenticate(User user) {
        String storedPassword = userDatabase.get(user.getUsername());
        return storedPassword != null && storedPassword.equals(user.getPassword());
    }
}
