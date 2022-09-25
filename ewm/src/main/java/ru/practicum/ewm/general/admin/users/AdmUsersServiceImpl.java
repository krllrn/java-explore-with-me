package ru.practicum.ewm.general.admin.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;
import ru.practicum.ewm.repositories.UserRepository;

import java.util.List;
import java.util.Map;

@Service
public class AdmUsersServiceImpl implements AdmUsersService {

    private final UserRepository userRepository;

    @Autowired
    public AdmUsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
