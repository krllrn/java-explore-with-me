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
import ru.practicum.ewm.models.request.Request;
import ru.practicum.ewm.models.request.RequestStatus;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.RequestRepository;
import ru.practicum.ewm.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RequestRepositoryTest {
    private final RequestRepository requestRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final Category category = new Category("Category");
    private final User user = new User("test@email.ru", "Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final Comment comment = new Comment(event, "Text", user.getName(), LocalDateTime.now());
    private final Request request = new Request(LocalDateTime.now(), null, null, RequestStatus.PENDING);

    @Autowired
    public RequestRepositoryTest(RequestRepository requestRepository, CommentRepository commentRepository, EventRepository eventRepository,
                                 CategoryRepository categoryRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
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
        request.setEvent(event.getId());
        request.setRequester(user.getId());
        requestRepository.save(request);
    }

    @Test
    public void testFindAllByEventId() {
        List<Request> reqList = requestRepository.findAllByEventId(event.getId());

        Assertions.assertEquals(1, reqList.size());
    }

    @Test
    public void testFindAllByEventIdAndStatus() {
        List<Request> reqList = requestRepository.findAllByEventIdAndStatus(event.getId(), RequestStatus.PENDING);
        List<Request> reqListZero = requestRepository.findAllByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED);

        Assertions.assertEquals(1, reqList.size());
        Assertions.assertEquals(0, reqListZero.size());
    }

    @Test
    public void testFindAllByRequesterId() {
        List<Request> reqList = requestRepository.findAllByRequesterId(user.getId());

        Assertions.assertEquals(1, reqList.size());
    }

    @Test
    public void testFindByRequesterAndEvent() {
        Request founded = requestRepository.findByRequesterAndEvent(event.getId(), user.getId());

        Assertions.assertEquals(request.getRequester(), founded.getRequester());
        Assertions.assertEquals(request.getEvent(), founded.getEvent());
    }

    @Test
    public void testFindByIdIs() {
        Request founded = requestRepository.findByIdIs(request.getId());

        Assertions.assertEquals(request.getRequester(), founded.getRequester());
        Assertions.assertEquals(request.getStatus(), founded.getStatus());
    }
}
