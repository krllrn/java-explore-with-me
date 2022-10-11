package ru.practicum.ewm.RepositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventStates;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class EventRepositoryTest {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final Category category = new Category("Category");
    private final User user = new User("test@email.ru" ,"Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final Comment comment = new Comment(event.getId(), "Text", user.getName(), LocalDateTime.now());

    @Autowired
    public EventRepositoryTest(CommentRepository commentRepository, EventRepository eventRepository,
                               CategoryRepository categoryRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        event.setState(EventStates.PENDING);
        categoryRepository.save(category);
        userRepository.save(user);
        eventRepository.save(event);
        commentRepository.save(comment);
    }

    @Test
    public void testFindByIdIs() {
        Event founded = eventRepository.findByIdIs(event.getId());

        Assertions.assertEquals(event.getId(), founded.getId());
        Assertions.assertEquals(event.getAnnotation(), founded.getAnnotation());
        Assertions.assertEquals(event.getDescription(), founded.getDescription());
    }

    @Test
    public void testFindEventsByCategory() {
        List<Event> eventList = eventRepository.findEventsByCategory(category.getId());

        Assertions.assertEquals(1, eventList.size());
    }

    @Test
    public void testFindEventsByInitiator() {
        List<Event> eventList = eventRepository.findEventsByInitiator(user.getId());

        Assertions.assertEquals(1, eventList.size());
    }

    @Test
    public void testFindEventsByState() {
        List<Event> eventList = eventRepository.findEventsByState(EventStates.PENDING.toString());

        Assertions.assertEquals(1, eventList.size());
    }
}
