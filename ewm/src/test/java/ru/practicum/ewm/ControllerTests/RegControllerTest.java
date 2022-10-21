package ru.practicum.ewm.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.general.reg.events.RegEventController;
import ru.practicum.ewm.general.reg.events.RegEventService;
import ru.practicum.ewm.general.reg.requests.RegRequestController;
import ru.practicum.ewm.general.reg.requests.RegRequestService;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.request.RequestDto;
import ru.practicum.ewm.models.request.RequestStatus;
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

@WebMvcTest({RegEventController.class, RegRequestController.class})
public class RegControllerTest {
    @MockBean
    RegEventService regEventService;

    @MockBean
    RegRequestService regRequestService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    private final EventShortDto eventShortDto = new EventShortDto("Annotation", new Category(), "Description", LocalDateTime.now(),
            new UserShortDto(), true, "Title");
    private final EventFullDto eventFullDto = new EventFullDto("Annotation", new Category(1L, "Category"), LocalDateTime.now(),
            new UserShortDto(1L, "Name"), new Location(), false, "Title");
    private final NewEventDto newEventDto = new NewEventDto("Annotation", 2L, "Description", LocalDateTime.now().plusDays(4), new Location(), "Title");
    private final UpdateEventRequest updateEventRequest = new UpdateEventRequest("Annotation", 2L, "Description", LocalDateTime.now().plusDays(2),
            5L, true, 15, "Title");

    private final RequestDto requestDto = new RequestDto(LocalDateTime.now().plusDays(1), 1L, 2L, 5L, RequestStatus.PENDING);

    private final CommentShortDto commentShortDto = new CommentShortDto("Comment");
    private final CommentDto commentDto = new CommentDto(1L, 5L, "Comment", new UserShortDto(3L, "Troll"), LocalDateTime.now());

    @Test
    public void testGetCurrentUserEvents() throws Exception {
        List<EventShortDto> eventList = new ArrayList<>();
        eventList.add(eventShortDto);

        when(regEventService.getCurrentUserEvents(any(Long.class), any(Integer.class), any(Integer.class))).thenReturn(eventList);

        mvc.perform(get("/users/1/events")
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
        when(regEventService.editEvent(any(Long.class), any(UpdateEventRequest.class))).thenReturn(eventFullDto);

        mvc.perform(patch("/users/1/events")
                    .content(mapper.writeValueAsString(updateEventRequest))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.description", is(eventFullDto.getDescription())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())));
    }

    @Test
    public void testAddEvent() throws Exception {
        when(regEventService.addEvent(any(Long.class), any(NewEventDto.class))).thenReturn(eventFullDto);

        mvc.perform(post("/users/1/events")
                        .content(mapper.writeValueAsString(newEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.description", is(eventFullDto.getDescription())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())));
    }

    @Test
    public void testGetEventByIdCurrentUser() throws Exception {
        when(regEventService.getEventByIdCurrentUser(any(Long.class), any(Long.class))).thenReturn(eventFullDto);

        mvc.perform(get("/users/1/events/5")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.description", is(eventFullDto.getDescription())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())));
    }

    @Test
    public void testCancelEvent() throws Exception {
        eventFullDto.setState(EventStates.CANCELED);
        when(regEventService.cancelEvent(any(Long.class), any(Long.class))).thenReturn(eventFullDto);

        mvc.perform(patch("/users/1/events/5")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annotation", is(eventFullDto.getAnnotation())))
                .andExpect(jsonPath("$.description", is(eventFullDto.getDescription())))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle())))
                .andExpect(jsonPath("$.state", is(eventFullDto.getState().toString())));
    }

    @Test
    public void testGetEventRequests() throws Exception {
        List<RequestDto> reqList = new ArrayList<>();
        reqList.add(requestDto);

        when(regEventService.getEventRequests(any(Long.class), any(Long.class))).thenReturn(reqList);

        mvc.perform(get("/users/1/events/5/requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testConfirmEventRequest() throws Exception {
        requestDto.setStatus(RequestStatus.CONFIRMED);
        when(regEventService.confirmEventRequest(any(Long.class), any(Long.class), any(Long.class))).thenReturn(requestDto);

        mvc.perform(patch("/users/1/events/5/requests/2/confirm")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(requestDto.getStatus().toString())));
    }

    @Test
    public void testRejectEventRequest() throws Exception {
        requestDto.setStatus(RequestStatus.REJECTED);
        when(regEventService.rejectEventRequest(any(Long.class), any(Long.class), any(Long.class))).thenReturn(requestDto);

        mvc.perform(patch("/users/1/events/5/requests/2/reject")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(requestDto.getStatus().toString())));
    }

    @Test
    public void testAddComment() throws Exception {
        when(regEventService.addComment(any(Long.class), any(Long.class), any(CommentShortDto.class))).thenReturn(commentDto);

        mvc.perform(post("/users/1/events/2/comments")
                    .content(mapper.writeValueAsString(commentShortDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(commentDto.getText())));
    }

    @Test
    public void testEditComment() throws Exception {
        when(regEventService.editComment(any(Long.class), any(Long.class), any(Long.class), any(CommentShortDto.class)))
                .thenReturn(commentDto);

        mvc.perform(patch("/users/1/events/2/comments/5")
                        .content(mapper.writeValueAsString(commentShortDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(commentDto.getText())));
    }

    @Test
    public void testDeleteComment() throws Exception {
        mvc.perform(delete("/users/1/events/2/comments/5")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ----------------------- REQUESTS ---------------------------------------

    @Test
    public void testGetUserRequests() throws Exception {
        List<RequestDto> reqList = new ArrayList<>();
        reqList.add(requestDto);

        when(regRequestService.getUserRequests(any(Long.class))).thenReturn(reqList);

        mvc.perform(get("/users/1/requests")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testAddRequestCurrentUser() throws Exception {
        when(regRequestService.addRequestCurrentUser(any(Long.class), any(Long.class))).thenReturn(requestDto);

        mvc.perform(post("/users/1/requests")
                        .param("eventId", String.valueOf(1))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(requestDto.getStatus().toString())))
                .andExpect(jsonPath("$.requester", is(requestDto.getRequester()), Long.class));
    }

    @Test
    public void testCancelRequest() throws Exception {
        requestDto.setStatus(RequestStatus.CANCELED);
        when(regRequestService.cancelRequest(any(Long.class), any(Long.class))).thenReturn(requestDto);

        mvc.perform(patch("/users/1/requests/1/cancel")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(requestDto.getStatus().toString())))
                .andExpect(jsonPath("$.requester", is(requestDto.getRequester()), Long.class));
    }
}
