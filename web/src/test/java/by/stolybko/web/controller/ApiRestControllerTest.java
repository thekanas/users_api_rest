package by.stolybko.web.controller;

import by.stolybko.database.dto.UserDTO;
import by.stolybko.database.entity.UserEntity;
import by.stolybko.database.entity.enam.Role;
import by.stolybko.service.UserService;
import by.stolybko.web.util.UserNotCreatedException;
import by.stolybko.web.util.UsersNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ApiRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private ApiRestController apiRestController;

    @Test
    void whenShowUsersInvoked_ThenReturnedHttpStatusOk() {
        Integer page = 1;
        Integer limit = 10;

        List<UserDTO> userDTOList = Arrays.asList(
                UserDTO.builder()
                        .fullName("Kalashnikov Appolon Romanovich")
                        .email("kalashnikov@mail.ru")
                        .role("SALE_USER")
                        .build(),
                UserDTO.builder()
                        .fullName("Sharova Taisiya Maksimovna")
                        .email("sharova@mail.ru")
                        .role("CUSTOMER_USER")
                        .build()
        );

        when(userService.showAllOrderByEmail(page, limit)).thenReturn(userDTOList);

        ResponseEntity<?> responseEntity = apiRestController.showUsers(page, limit);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void whenAddUserInvoked_ThenReturnedHttpStatusOk() {
        UserEntity userRequest = UserEntity.builder()
                .lastName("Kalashnikov")
                .name("Appolon")
                .patronymic("Romanovich")
                .email("kalashnikov@mail.ru")
                .role(Role.valueOf("SALE_USER"))
                .build();
        Long userResponse = 1L;

        when(userService.save(userRequest)).thenReturn(userResponse);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> responseEntity = apiRestController.addUser(userRequest, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void whenShowUsersInvokedWithEmptyList_ThenThrowUsersNotFoundException() {
        Integer page = 1;
        Integer limit = 10;

        when(userService.showAllOrderByEmail(page, limit)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(UsersNotFoundException.class, () -> {
            apiRestController.showUsers(page, limit);
        });

        String expectedMessage = "no users to display";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenShowUsersInvokedWithBadData_ThenUserNotCreatedException() {
        UserEntity userRequest = UserEntity.builder()
                .lastName("Kalashnikov")
                .name("Appolon")
                .patronymic("Romanovich")
                .email("kalashnikov@mail.ru")
                .role(Role.valueOf("SALE_USER"))
                .build();

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(UserNotCreatedException.class, () -> {
            apiRestController.addUser(userRequest, bindingResult);
        });
    }
}