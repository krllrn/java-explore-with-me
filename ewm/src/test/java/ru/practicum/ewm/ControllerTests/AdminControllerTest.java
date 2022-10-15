package ru.practicum.ewm.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.general.admin.categories.AdmCategoriesController;
import ru.practicum.ewm.general.admin.categories.AdmCategoriesService;
import ru.practicum.ewm.general.admin.compilations.AdmCompilationController;
import ru.practicum.ewm.general.admin.compilations.AdmCompilationService;
import ru.practicum.ewm.general.admin.events.AdmEventController;
import ru.practicum.ewm.general.admin.events.AdmEventService;
import ru.practicum.ewm.general.admin.users.AdmUsersController;
import ru.practicum.ewm.general.admin.users.AdmUsersService;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventStates;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;
import ru.practicum.ewm.models.user.UserShortDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AdmCategoriesController.class, AdmCompilationController.class, AdmEventController.class, AdmUsersController.class})
public class AdminControllerTest {
    @MockBean
    AdmCategoriesService admCategoriesService;

    @MockBean
    AdmCompilationService admCompilationService;

    @MockBean
    AdmEventService admEventService;

    @MockBean
    AdmUsersService admUsersService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private final CategoryDto categoryDto = new CategoryDto(1L, "CategoryDto");
    private final NewCategoryDto newCategoryDto = new NewCategoryDto("NewCategoryDto");

    private final CompilationDto compilationDto = new CompilationDto(new ArrayList<>(), 1L, false, "Title");
    private final NewCompilationDto newCompilationDto = new NewCompilationDto("NewTitle");

    private final EventFullDto eventFullDto = new EventFullDto("Annotation", new Category(1L, "Category"), LocalDateTime.now(),
            new UserShortDto(1L, "Name"), new Location(), false, "Title");
    private final AdminUpdateEventRequest adminUpdateEventRequest = new AdminUpdateEventRequest("Annotation", 1L, "Description",
            LocalDateTime.now().plusDays(1), new Location(), false, 10, true, "Title");

    private final CommentDto commentDto = new CommentDto(1L, 2L, "Comment", "Troll", LocalDateTime.now());
    private final CommentShortDto commentShortDto = new CommentShortDto("ShortComment");

    private final UserDto userDto = new UserDto("new@email.ru", 1L, "Troll");
    private final NewUserRequest newUserRequest = new NewUserRequest("new@email.ru", "NewTroll");

    @Test
    public void testEditCategory() throws Exception {
        when(admCategoriesService.editCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        mvc.perform(patch("/admin/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName())));
    }

    @Test
    public void testAddCategory() throws Exception {
        when(admCategoriesService.addCategory(any(NewCategoryDto.class))).thenReturn(categoryDto);

        mvc.perform(post("/admin/categories")
                        .content(objectMapper.writeValueAsString(newCategoryDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName())));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mvc.perform(delete("/admin/categories/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ---------------- COMPILATIONS ------------------------------------

    @Test
    public void testAddCompilation() throws Exception {
        when(admCompilationService.addCompilation(any(NewCompilationDto.class))).thenReturn(compilationDto);

        mvc.perform(post("/admin/compilations")
                        .content(objectMapper.writeValueAsString(newCompilationDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(compilationDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(compilationDto.getTitle())))
                .andExpect(jsonPath("$.pinned", is(compilationDto.getPinned())));
    }

    @Test
    public void testDeleteCompilation() throws Exception {
        mvc.perform(delete("/admin/compilations/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEventFromCompilation() throws Exception {
        mvc.perform(delete("/admin/compilations/1/events/3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEventToCompilation() throws Exception {
        mvc.perform(patch("/admin/compilations/1/events/3")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnpinCompilation() throws Exception {
        mvc.perform(delete("/admin/compilations/1/pin")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPinCompilation() throws Exception {
        mvc.perform(patch("/admin/compilations/1/pin")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ----------------------------- EVENTS ----------------------------------

    @Test
    public void testGetEvents() throws Exception {
        List<EventFullDto> eventList = new ArrayList<>();
        eventList.add(eventFullDto);
        when(admEventService.getEvents(any())).thenReturn(eventList);

        mvc.perform(get("/admin/events")
                        .param("users", String.valueOf(1))
                        .param("states", String.valueOf(EventStates.PENDING))
                        .param("categories", String.valueOf(1))
                        .param("rangeStart", String.valueOf(LocalDateTime.now()))
                        .param("rangeEnd", String.valueOf(LocalDateTime.now()))
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
    public void testEditEvent() throws Exception {
        when(admEventService.editEvent(any(Long.class), any(AdminUpdateEventRequest.class))).thenReturn(eventFullDto);

        mvc.perform(put("/admin/events/1")
                    .content(objectMapper.writeValueAsString(adminUpdateEventRequest))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())));
    }

    @Test
    public void testPublishEvent() throws Exception {
        eventFullDto.setState(EventStates.PUBLISHED);
        when(admEventService.publishEvent(any(Long.class))).thenReturn(eventFullDto);

        mvc.perform(patch("/admin/events/1/publish")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())))
                .andExpect(jsonPath("$.state", is(eventFullDto.getState().toString())));
    }

    @Test
    public void testRejectEvent() throws Exception {
        eventFullDto.setState(EventStates.CANCELED);
        when(admEventService.rejectEvent(any(Long.class))).thenReturn(eventFullDto);

        mvc.perform(patch("/admin/events/1/reject")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())))
                .andExpect(jsonPath("$.state", is(eventFullDto.getState().toString())));
    }

    // ------------------- COMMENTS -----------------------------------------------------

    @Test
    public void testEditComment() throws Exception {
        when(admEventService.editComment(any(Long.class), any(Long.class),any(CommentShortDto.class))).thenReturn(commentDto);

        mvc.perform(patch("/admin/events/1/comments/2")
                        .content(objectMapper.writeValueAsString(commentShortDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.text", is(commentDto.getText())));
    }

    // ------------------------------- USERS --------------------------------------------------

    @Test
    public void testGetUsers() throws Exception {
        List<UserDto> userList = new ArrayList<>();
        userList.add(userDto);
        when(admUsersService.getUsers(any(), any(Integer.class), any(Integer.class))).thenReturn(userList);

        mvc.perform(get("/admin/users")
                    .param("ids", String.valueOf(1))
                    .param("from", String.valueOf(1))
                    .param("size", String.valueOf(10))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testAddUser() throws Exception {
        when(admUsersService.addUser(any(NewUserRequest.class))).thenReturn(userDto);

        mvc.perform(post("/admin/users")
                        .content(objectMapper.writeValueAsString(newUserRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.name", is(userDto.getName())));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(delete("/admin/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
