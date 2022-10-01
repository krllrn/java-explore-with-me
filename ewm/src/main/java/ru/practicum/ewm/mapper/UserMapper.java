package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserDto;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User newToEntity(NewUserRequest newUserRequest) {
        return modelMapper.map(newUserRequest, User.class);
    }
}
