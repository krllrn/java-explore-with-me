package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.admin.compilations.AdmCompilationServiceImpl;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;
import ru.practicum.ewm.repositories.EventRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class AdmCompilationServiceTest {
    @Mock
    private CompilationRepository compilationRepository;

    @Mock
    private CompilationMapper compilationMapper;

    @Mock
    private EventRepository eventRepository;

    private final Compilation compilation = new Compilation(new ArrayList<>(), true, "Title");
    private final CompilationDto compilationDto = new CompilationDto(new ArrayList<>(), 1L, true, "Title");
    private final NewCompilationDto newCompilationDto = new NewCompilationDto("Title");

    @Test
    public void testAddCompilation() {
        AdmCompilationServiceImpl admCompilationService = new AdmCompilationServiceImpl(compilationRepository, compilationMapper, eventRepository);
        Mockito.when(compilationMapper.newCompToEntity(any(NewCompilationDto.class))).thenReturn(compilation);
        Mockito.when(compilationRepository.save(any(Compilation.class))).thenReturn(compilation);
        Mockito.when(compilationMapper.compEntityToDto(any(Compilation.class))).thenReturn(compilationDto);

        CompilationDto founded = admCompilationService.addCompilation(newCompilationDto);

        Assertions.assertEquals(compilationDto.getTitle(), founded.getTitle());
        Assertions.assertEquals(compilationDto.getPinned(), founded.getPinned());
    }
}
