package ru.practicum.ewm.general.pub.compilations;

import ru.practicum.ewm.models.compilation.CompilationDto;

import java.util.List;
import java.util.Map;

public interface PubCompilationsService {
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompById(Long compId);
}
