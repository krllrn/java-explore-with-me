package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.pub.compilations.PubCompilationsServiceImpl;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.repositories.CompilationRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class PubCompilationServiceTest {
    @Mock
    private CompilationRepository compilationRepository;

    @Mock
    private CompilationMapper compilationMapper;

    private final Compilation compilation = new Compilation(new ArrayList<>(), true, "Title");
    private final CompilationDto compilationDto = new CompilationDto(new ArrayList<>(), 1L, false, "Title");

    @Test
    public void testGetCompById() {
        PubCompilationsServiceImpl pubCompilationsService = new PubCompilationsServiceImpl(compilationRepository,
                compilationMapper);
        Mockito.when(compilationRepository.findByIdIs(any(Long.class))).thenReturn(compilation);
        Mockito.when(compilationMapper.compEntityToDto(any(Compilation.class))).thenReturn(compilationDto);

        CompilationDto founded = pubCompilationsService.getCompById(1L);

        Assertions.assertEquals(compilationDto.getTitle(), founded.getTitle());
        Assertions.assertEquals(compilationDto.getPinned(), founded.getPinned());
    }
}
