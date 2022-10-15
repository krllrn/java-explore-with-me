package ru.practicum.ewm.general.pub.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PubCompilationsServiceImpl implements PubCompilationsService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<CompilationDto> compilationList;
        if (pinned != null) {
            compilationList = compilationRepository.findByPinned(pinned).stream()
                    .map(compilationMapper::compEntityToDto)
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        } else {
            compilationList = compilationRepository.findAll().stream()
                    .map(compilationMapper::compEntityToDto)
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        }
        return compilationList;
    }

    @Override
    public CompilationDto getCompById(Long compId) {
        if (compId == null) {
            throw new ForbiddenHandler("Compilation ID can't be NULL.");
        }
        if (compilationRepository.findByIdIs(compId) == null) {
            throw new NotFoundHandler("Compilation not found.");
        }
        return compilationMapper.compEntityToDto(compilationRepository.findByIdIs(compId));
    }
}
