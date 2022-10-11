package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.pub.events.PubEventServiceImpl;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.statistic.Statistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class PubEventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private Statistic statistic;

    @PersistenceContext
    private EntityManager entityManager;

    private final Category category = new Category(1L, "Category");
    private final User user = new User("test@email.ru" ,"Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final EventShortDto eventShortDto = new EventShortDto("Annotation", category, "Description", LocalDateTime.now(),
            new UserShortDto(), true, "Title");

    @Test
    public void testGetEventById() {
        PubEventServiceImpl pubEventService = new PubEventServiceImpl(eventRepository, eventMapper, statistic,
                entityManager);
        Mockito.when(eventRepository.findByIdIs(any(Long.class))).thenReturn(event);
        Mockito.when(eventMapper.entityToShort(any(Event.class))).thenReturn(eventShortDto);

        EventShortDto founded = pubEventService.getEventById(1L, null);

        Assertions.assertEquals(eventShortDto.getDescription(), founded.getDescription());
        Assertions.assertEquals(eventShortDto.getAnnotation(), founded.getAnnotation());
    }
}
