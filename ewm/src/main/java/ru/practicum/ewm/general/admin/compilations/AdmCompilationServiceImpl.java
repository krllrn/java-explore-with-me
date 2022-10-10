package ru.practicum.ewm.general.admin.compilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;
import ru.practicum.ewm.repositories.EventRepository;

@Service
public class AdmCompilationServiceImpl implements AdmCompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Autowired
    public AdmCompilationServiceImpl(CompilationRepository compilationRepository, CompilationMapper compilationMapper,
                                     EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.compilationMapper = compilationMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.newCompToEntity(newCompilationDto);
        return compilationMapper.compEntityToDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        if (compId == null) {
            throw new ForbiddenHandler("Compilation ID is null.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation with ID:" + compId + " not found.");
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        if (compId == null || eventId == null) {
            throw new ForbiddenHandler("Compilation or event ID is null.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation with ID:" + compId + " not found.");
        }
        Compilation compilation = compilationRepository.findByIdIs(compId);
        compilation.getEvents().remove(eventRepository.findByIdIs(eventId));
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        if (compId == null || eventId == null) {
            throw new ForbiddenHandler("Compilation or event ID is null.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation with ID:" + compId + " not found.");
        }
        Compilation compilation = compilationRepository.findByIdIs(compId);
        compilation.getEvents().add(eventRepository.findByIdIs(eventId));
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        if (compId == null) {
            throw new ForbiddenHandler("Compilation ID is null.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation with ID:" + compId + " not found.");
        }
        Compilation compilation = compilationRepository.findByIdIs(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        if (compId == null) {
            throw new ForbiddenHandler("Compilation ID is null.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation with ID:" + compId + " not found.");
        }
        Compilation compilation = compilationRepository.findByIdIs(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
