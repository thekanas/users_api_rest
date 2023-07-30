package by.stolybko.service;

import by.stolybko.database.dto.UserDTO;
import by.stolybko.database.entity.UserEntity;
import by.stolybko.database.entity.enam.Role;
import by.stolybko.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenShowAllOrderByEmailInvoked_ThenAllUsersReturned() {
        int page = 1;
        int limit = 10;

        List<UserEntity> userEntityList = Arrays.asList(
                UserEntity.builder()
                        .id(1L)
                        .lastName("Kalashnikov")
                        .name("Appolon")
                        .patronymic("Romanovich")
                        .email("kalashnikov@mail.ru")
                        .role(Role.valueOf("SALE_USER"))
                        .build(),
                UserEntity.builder()
                        .id(2L)
                        .lastName("Sharova")
                        .name("Taisiya")
                        .patronymic("Maksimovna")
                        .email("sharova@mail.ru")
                        .role(Role.valueOf("CUSTOMER_USER"))
                        .build()
        );
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
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> userEntityPage = new PageImpl<>(userEntityList, pageable, userEntityList.size());

        when(userRepository.findAll(PageRequest.of(page - 1, limit, Sort.by("email")))).thenReturn(userEntityPage);

        assertArrayEquals(userDTOList.toArray(), userService.showAllOrderByEmail(page, limit).toArray());
        verify(userRepository, times(1)).findAll(PageRequest.of(page - 1, limit, Sort.by("email")));
    }

    @Test
    void whenSaveInvoked_ThenIdReturned() {

        long id = 1L;

        UserEntity userEntity =
                UserEntity.builder()
                        .id(1L)
                        .lastName("Kalashnikov")
                        .name("Appolon")
                        .patronymic("Romanovich")
                        .email("kalashnikov@mail.ru")
                        .role(Role.valueOf("SALE_USER"))
                        .build();

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        assertEquals(id, userService.save(userEntity));
        verify(userRepository, times(1)).save(userEntity);

    }
}