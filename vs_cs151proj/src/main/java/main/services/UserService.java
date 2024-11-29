package main.services;

import main.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class to manage user operations such as authentication and registration.
 */
public class UserService {
    private static final List<User> users = new ArrayList<>();

    static {
        // Predefined users for demonstration purposes.
        users.add(new User("Alice", "Smith", "alice", "password123"));
        users.add(new User("Bob", "Jones", "bob", "securepass"));
    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return The authenticated User object, or null if authentication fails.
     */
    public static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Registers a new user.
     *
     * @param user The User object to be registered.
     */
    public static void registerUser(User user) {
        users.add(user);
    }

    /**
     * Gets the list of all registered users.
     *
     * @return A List of User objects.
     */
    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}

