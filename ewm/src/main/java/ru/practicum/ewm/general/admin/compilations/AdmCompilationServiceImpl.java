package ru.practicum.ewm.general.admin.compilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;

@Service
public class AdmCompilationServiceImpl implements AdmCompilationService {

    private final CompilationRepository compilationRepository;

    @Autowired
    public AdmCompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Long compId) {

    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {

    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {

    }

    @Override
    public void unpinCompilation(Long compId) {

    }

    @Override
    public void pinCompilation(Long compId) {

    }
}
