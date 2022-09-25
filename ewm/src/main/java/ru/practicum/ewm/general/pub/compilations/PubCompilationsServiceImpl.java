package ru.practicum.ewm.general.pub.compilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;

import java.util.List;
import java.util.Map;

@Service
public class PubCompilationsServiceImpl implements PubCompilationsService {

    private final CompilationRepository compilationRepository;

    @Autowired
    public PubCompilationsServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public CompilationDto getCompById(Long compId) {
        return null;
    }
}
