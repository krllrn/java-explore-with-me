package ru.practicum.ewm.general.admin.compilations;

import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;

public interface AdmCompilationService {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}
