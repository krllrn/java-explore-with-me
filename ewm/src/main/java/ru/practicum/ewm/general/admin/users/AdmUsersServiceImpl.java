package ru.practicum.ewm.general.admin.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;
import ru.practicum.ewm.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdmUsersServiceImpl implements AdmUsersService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AdmUsersServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<UserDto> users = new ArrayList<>();
        if (ids.size() != 0) {
            for (Long i : ids) {
                users.add(userMapper.entityToDto(userRepository.findByIdIs(i)));
            }
            users = users.stream()
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        } else {
            users = userRepository.findAll().stream()
                    .map(userMapper::entityToDto)
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        }
        return users;
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return userMapper.entityToDto(userRepository.save(userMapper.newToEntity(newUserRequest)));
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId <= 0) {
            throw new ForbiddenHandler("User ID can't be less than 1.");
        }
        if (userRepository.findByIdIs(userId) == null) {
            throw new NotFoundHandler("User with ID: " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
