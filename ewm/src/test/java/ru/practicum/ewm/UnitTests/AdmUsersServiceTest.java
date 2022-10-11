package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.admin.users.AdmUsersServiceImpl;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserDto;
import ru.practicum.ewm.repositories.UserRepository;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class AdmUsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private final User user = new User("test@email.ru", "Name");
    private final UserDto userDto = new UserDto("new@email.ru", 1L, "Troll");
    private final NewUserRequest newUserRequest = new NewUserRequest("new@email.ru", "NewTroll");

    @Test
    public void testAddUser() {
        AdmUsersServiceImpl admUsersService = new AdmUsersServiceImpl(userRepository, userMapper);
        Mockito.when(userMapper.newToEntity(any(NewUserRequest.class))).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userMapper.entityToDto(any(User.class))).thenReturn(userDto);

        UserDto founded = admUsersService.addUser(newUserRequest);

        Assertions.assertEquals(userDto.getName(), founded.getName());
        Assertions.assertEquals(userDto.getEmail(), founded.getEmail());
    }
}
