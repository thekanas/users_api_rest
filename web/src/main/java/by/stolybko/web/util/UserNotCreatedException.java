package by.stolybko.web.util;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String msq) {
        super(msq);
    }
}
