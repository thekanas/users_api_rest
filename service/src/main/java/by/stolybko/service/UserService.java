package by.stolybko.service;

import by.stolybko.database.dto.UserDTO;
import by.stolybko.database.entity.UserEntity;
import by.stolybko.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> showAllOrderByEmail(Integer page, Integer limit) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : userRepository.findAll(PageRequest.of(page - 1, limit, Sort.by("email")))) {
            userDTOList.add(UserDTO.builder()
                            .fullName(user.getLastName() + " " + user.getName() + " " + user.getPatronymic())
                            .email(user.getEmail())
                            .role(user.getRole().toString())
                            .build());
        }

        return userDTOList;
    }

    @Transactional
    public Long save(UserEntity user) {
        UserEntity saveUser = userRepository.save(user);
        return saveUser.getId();
    }
}
