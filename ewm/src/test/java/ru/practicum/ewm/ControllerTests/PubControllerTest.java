package ru.practicum.ewm.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.general.pub.categories.PubCategoriesController;
import ru.practicum.ewm.general.pub.categories.PubCategoriesService;
import ru.practicum.ewm.general.pub.compilations.PubCompilationsController;
import ru.practicum.ewm.general.pub.compilations.PubCompilationsService;
import ru.practicum.ewm.general.pub.events.PubEventController;
import ru.practicum.ewm.general.pub.events.PubEventService;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.user.UserShortDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PubCategoriesController.class, PubCompilationsController.class, PubEventController.class})
public class PubControllerTest {
    @MockBean
    PubCategoriesService pubCategoriesService;

    @MockBean
    PubCompilationsService pubCompilationsService;

    @MockBean
    PubEventService pubEventService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    private final CategoryDto categoryDto = new CategoryDto(1L, "CategoryDto");

    private final CompilationDto compilationDto = new CompilationDto(new ArrayList<>(), 1L, false, "Title");

    private final EventShortDto eventShortDto = new EventShortDto("Annotation", new Category(), "Description", LocalDateTime.now(),
            new UserShortDto(), true, "Title");

    @Test
    public void testGetCategories() throws Exception {
        List<CategoryDto> catList = new ArrayList<>();
        catList.add(categoryDto);

        when(pubCategoriesService.getCategories(any(), any())).thenReturn(catList);

        mvc.perform(get("/categories")
                        .param("from", String.valueOf(0))
                        .param("size", String.valueOf(5))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        when(pubCategoriesService.getCategoryById(any())).thenReturn(categoryDto);

        mvc.perform(get("/categories/1")
                        .content(mapper.writeValueAsString(categoryDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName())));
    }

    // --------------------- COMPILATIONS ----------------------------------------------

    @Test
    public void testGetCompilations() throws Exception {
        List<CompilationDto> compList = new ArrayList<>();
        compList.add(compilationDto);

        when(pubCompilationsService.getCompilations(any(Boolean.class), any(Integer.class), any(Integer.class)))
                .thenReturn(compList);

        mvc.perform(get("/compilations")
                        .param("pinned", String.valueOf(false))
                        .param("from", String.valueOf(0))
                        .param("size", String.valueOf(5))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testGetCompById() throws Exception {
        when(pubCompilationsService.getCompById(any())).thenReturn(compilationDto);

        mvc.perform(get("/compilations/1")
                        .content(mapper.writeValueAsString(compilationDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(compilationDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())));
    }

    // -------------------------------- EVENTS --------------------------------------------

    @Test
    public void testGetEvents() throws Exception {
        List<EventShortDto> eventsList = new ArrayList<>();
        eventsList.add(eventShortDto);

        when(pubEventService.getEvents(any(), any())).thenReturn(eventsList);

        mvc.perform(get("/events")
                        .param("text", "Text")
                        .param("categories", "1")
                        .param("paid", "false")
                        .param("rangeStart", "27.07.1988")
                        .param("rangeEnd", "27.07.2025")
                        .param("onlyAvailable", "true")
                        .param("sort", "EVENT_DATE")
                        .param("from", String.valueOf(0))
                        .param("size", String.valueOf(5))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testGetEventById() throws Exception {
        when(pubEventService.getEventById(any(Long.class), any())).thenReturn(eventShortDto);

        mvc.perform(get("/events/1")
                        .content(mapper.writeValueAsString(eventShortDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventShortDto.getAnnotation())))
                .andExpect(jsonPath("$.description", is(eventShortDto.getDescription())));
    }
}
