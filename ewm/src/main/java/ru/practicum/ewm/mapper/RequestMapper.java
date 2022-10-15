package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.request.Request;
import ru.practicum.ewm.models.request.RequestDto;

@Component
public class RequestMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public RequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RequestDto entityToDto(Request request) {
        return modelMapper.map(request, RequestDto.class);
    }
}
