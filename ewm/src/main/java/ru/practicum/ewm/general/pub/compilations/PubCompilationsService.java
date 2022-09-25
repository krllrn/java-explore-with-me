package ru.practicum.ewm.general.pub.compilations;

import ru.practicum.ewm.models.compilation.CompilationDto;

import java.util.List;
import java.util.Map;

public interface PubCompilationsService {
    List<CompilationDto> getCompilations(Map<String, Object> parameters);

    CompilationDto getCompById(Long compId);
}
