package by.stolybko.web.util;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException() {
        super("no users to display");
    }
}
