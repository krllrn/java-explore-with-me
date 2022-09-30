package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;

@Component
public class CompilationMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public CompilationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Compilation newCompToEntity(NewCompilationDto newCompilationDto) {
        return modelMapper.map(newCompilationDto, Compilation.class);
    }

    public Compilation compDtoToEntity(CompilationDto compilationDto) {
        return modelMapper.map(compilationDto, Compilation.class);
    }

    public CompilationDto compEntityToDto(Compilation compilation) {
        return modelMapper.map(compilation, CompilationDto.class);
    }
}
