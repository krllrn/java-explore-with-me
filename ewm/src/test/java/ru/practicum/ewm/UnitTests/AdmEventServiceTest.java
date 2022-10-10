package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.admin.events.AdmEventServiceImpl;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class AdmEventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private final Category category = new Category(1L, "Category");
    private final User user = new User("test@email.ru" ,"Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final AdminUpdateEventRequest adminUpdateEventRequest = new AdminUpdateEventRequest("Annotation", 1L, "Description",
            LocalDateTime.now().plusDays(1), new Location(), false, 10, true, "Title");
    private final EventFullDto eventFullDto = new EventFullDto("Annotation", new Category(1L, "Category"), LocalDateTime.now(),
            new UserShortDto(1L, "Name"), new Location(), false, "Title");

    @Test
    public void testEditEvent() {
        AdmEventServiceImpl admEventService = new AdmEventServiceImpl(eventRepository, eventMapper, commentRepository, commentMapper, entityManager);
        Mockito.when(eventRepository.findByIdIs(any(Long.class))).thenReturn(event);
        Mockito.when(eventMapper.updateToEntity(any(Event.class), any(AdminUpdateEventRequest.class))).thenReturn(event);
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        Mockito.when(eventMapper.entityToFullDto(any(Event.class))).thenReturn(eventFullDto);

        EventFullDto founded = admEventService.editEvent(1L, adminUpdateEventRequest);

        Assertions.assertEquals(eventFullDto.getAnnotation(), founded.getAnnotation());
        Assertions.assertEquals(eventFullDto.getTitle(), founded.getTitle());
    }
}
