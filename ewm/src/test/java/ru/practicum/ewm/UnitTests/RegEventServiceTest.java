package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.reg.events.RegEventServiceImpl;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.RequestRepository;
import ru.practicum.ewm.repositories.UserRepository;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class RegEventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private RequestMapper requestMapper;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    private final Category category = new Category(1L, "Category");
    private final User user = new User(1L,"test@email.ru" ,"Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final EventShortDto eventShortDto = new EventShortDto("Annotation", category, "Description", LocalDateTime.now(),
            new UserShortDto(), true, "Title");
    private final UpdateEventRequest updateEventRequest = new UpdateEventRequest("Annotation", 4L, "Description",
            LocalDateTime.now().plusDays(1), 3L, true, 10, "Title");
    private final EventFullDto eventFullDto = new EventFullDto("Annotation", category,
            LocalDateTime.now().plusDays(1), new UserShortDto(1L, "Name"), new Location(), true, "Title");
    private final NewEventDto newEventDto = new NewEventDto("Annotation", 4L, "Description",
            LocalDateTime.now().plusDays(1), new Location(), "Title");

    @Test
    public void testEditEvent() {
        RegEventServiceImpl regEventService = new RegEventServiceImpl(eventRepository, userRepository,
                requestRepository, eventMapper, requestMapper, commentMapper, commentRepository);
        event.setState(EventStates.PENDING);
        Mockito.when(userRepository.findByIdIs(any(Long.class))).thenReturn(user);
        Mockito.when(eventRepository.findByIdIs(any(Long.class))).thenReturn(event);
        Mockito.when(eventMapper.updateReqToEntity(any(Event.class), any(UpdateEventRequest.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        Mockito.when(eventMapper.entityToFullDto(any(Event.class))).thenReturn(eventFullDto);

        EventFullDto founded = regEventService.editEvent(1L, updateEventRequest);

        Assertions.assertEquals(eventFullDto.getAnnotation(), founded.getAnnotation());
        Assertions.assertEquals(eventFullDto.getTitle(), founded.getTitle());
        Assertions.assertEquals(eventFullDto.getEventDate(), founded.getEventDate());
    }

    @Test
    public void testAddEvent() {
        RegEventServiceImpl regEventService = new RegEventServiceImpl(eventRepository, userRepository,
                requestRepository, eventMapper, requestMapper, commentMapper, commentRepository);
        event.setState(EventStates.PENDING);
        Mockito.when(userRepository.findByIdIs(any(Long.class))).thenReturn(user);
        Mockito.when(eventMapper.newToEntity(any(User.class), any(NewEventDto.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        Mockito.when(eventMapper.entityToFullDto(any(Event.class))).thenReturn(eventFullDto);

        EventFullDto founded = regEventService.addEvent(1L, newEventDto);

        Assertions.assertEquals(eventFullDto.getAnnotation(), founded.getAnnotation());
        Assertions.assertEquals(eventFullDto.getTitle(), founded.getTitle());
        Assertions.assertEquals(eventFullDto.getEventDate(), founded.getEventDate());
    }

    @Test
    public void testGetEventByIdCurrentUser() {
        RegEventServiceImpl regEventService = new RegEventServiceImpl(eventRepository, userRepository,
                requestRepository, eventMapper, requestMapper, commentMapper, commentRepository);
        event.setState(EventStates.PENDING);
        Mockito.when(userRepository.findByIdIs(any(Long.class))).thenReturn(user);
        Mockito.when(eventRepository.findByIdIs(any(Long.class))).thenReturn(event);
        Mockito.when(eventMapper.entityToFullDto(any(Event.class))).thenReturn(eventFullDto);

        EventFullDto founded = regEventService.getEventByIdCurrentUser(1L, 1L);

        Assertions.assertEquals(eventFullDto.getAnnotation(), founded.getAnnotation());
        Assertions.assertEquals(eventFullDto.getTitle(), founded.getTitle());
        Assertions.assertEquals(eventFullDto.getEventDate(), founded.getEventDate());
    }
}
