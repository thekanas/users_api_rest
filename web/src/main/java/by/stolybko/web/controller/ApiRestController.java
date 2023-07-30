package by.stolybko.web.controller;

import by.stolybko.database.dto.UserDTO;
import by.stolybko.database.entity.UserEntity;
import by.stolybko.service.UserService;
import by.stolybko.web.util.UserErrorResponse;
import by.stolybko.web.util.UserNotCreatedException;
import by.stolybko.web.util.UsersNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiRestController {

    private static final Logger LOGGER = LogManager.getLogger(ApiRestController.class);

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> showUsers(@RequestParam(defaultValue = "1") @Valid() Integer page, @RequestParam(defaultValue = "10") Integer limit) {

        LOGGER.info("Request to show users on page: {}, limit {}", page, limit);

        List<UserDTO> userDTOList = userService.showAllOrderByEmail(page, limit);
        if (userDTOList.size() < 1) {
            LOGGER.error("Error show users: no users to display");
            throw new UsersNotFoundException();
        }

        return ResponseEntity.ok(userService.showAllOrderByEmail(page, limit));
    }

    @PostMapping("/users")
    public ResponseEntity<Long> addUser(@RequestBody @Valid UserEntity user,
                                              BindingResult bindingResult) {
        LOGGER.info("Request to add a new user: {}", user);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            LOGGER.error("Error adding a user: {}", errorMsg.toString());
            throw new UserNotCreatedException(errorMsg.toString());
        }

        Long id = userService.save(user);
        LOGGER.info("User {} successfully added with id {}", user, id);
        return ResponseEntity.ok(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UsersNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(DataIntegrityViolationException e) {
        UserErrorResponse response = new UserErrorResponse(
                Objects.requireNonNull(e.getRootCause()).getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(IllegalArgumentException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
