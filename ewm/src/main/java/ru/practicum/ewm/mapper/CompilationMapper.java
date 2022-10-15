package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.repositories.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public CompilationMapper(ModelMapper modelMapper, EventRepository eventRepository, EventMapper eventMapper) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public Compilation newCompToEntity(NewCompilationDto newCompilationDto) {
        List<Event> eventList = new ArrayList<>();
        Compilation compilation = modelMapper.map(newCompilationDto, Compilation.class);
        for (Long i : newCompilationDto.getEvents()) {
            eventList.add(eventRepository.findByIdIs(i));
        }
        compilation.setEvents(eventList);
        return compilation;
    }

    public Compilation compDtoToEntity(CompilationDto compilationDto) {
        List<Event> eventList = new ArrayList<>();
        Compilation compilation = modelMapper.map(compilationDto, Compilation.class);
        for (EventShortDto i : compilationDto.getEvents()) {
            eventList.add(eventRepository.findByIdIs(i.getId()));
        }
        compilation.setEvents(eventList);
        return compilation;
    }

    public CompilationDto compEntityToDto(Compilation compilation) {
        List<EventShortDto> eventShortDtoList = compilation.getEvents().stream()
                .map(eventMapper::entityToShort)
                .collect(Collectors.toList());
        CompilationDto compilationDto = modelMapper.map(compilation, CompilationDto.class);
        compilationDto.setEvents(eventShortDtoList);
        return compilationDto;
    }
}
