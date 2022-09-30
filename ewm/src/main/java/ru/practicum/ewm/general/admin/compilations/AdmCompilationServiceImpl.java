package ru.practicum.ewm.general.admin.compilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;

@Service
public class AdmCompilationServiceImpl implements AdmCompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Autowired
    public AdmCompilationServiceImpl(CompilationRepository compilationRepository, CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.compilationMapper = compilationMapper;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.newCompToEntity(newCompilationDto);
        return compilationMapper.compEntityToDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        if (compId == null) {
            throw new BadRequestHandler("Compilation ID is null.");
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        if (compId == null || eventId == null) {
            throw new BadRequestHandler("Compilation or event ID is null.");
        }
        Compilation compilation = compilationRepository.getCompById(compId);
        compilation.getEvents().remove(eventId);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        if (compId == null || eventId == null) {
            throw new BadRequestHandler("Compilation or event ID is null.");
        }
        Compilation compilation = compilationRepository.getCompById(compId);
        compilation.getEvents().add(eventId);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        if (compId == null) {
            throw new BadRequestHandler("Compilation ID is null.");
        }
        Compilation compilation = compilationRepository.getCompById(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        if (compId == null) {
            throw new BadRequestHandler("Compilation ID is null.");
        }
        Compilation compilation = compilationRepository.getCompById(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
