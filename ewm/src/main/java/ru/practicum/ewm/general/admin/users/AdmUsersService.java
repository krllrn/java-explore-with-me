package ru.practicum.ewm.general.admin.users;

import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;

import java.util.List;
import java.util.Map;

public interface AdmUsersService {
    List<UserDto> getUsers(Map<String, Object> parameters);

    UserDto addUser(NewUserRequest newUserRequest);

    void deleteUser(Long userId);
}
