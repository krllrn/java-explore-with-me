package ru.practicum.ewm.RepositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.compilation.Compilation;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.CompilationRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CompilationRepositoryTest {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final Category category = new Category("Category");
    private final User user = new User("test@email.ru" ,"Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");
    private final Compilation compilation = new Compilation(new ArrayList<>(), true, "Title");

    @Autowired
    public CompilationRepositoryTest(CompilationRepository compilationRepository, EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByIdIs() {
        categoryRepository.save(category);
        userRepository.save(user);
        eventRepository.save(event);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
        Compilation founded = compilationRepository.findByIdIs(compilation.getId());

        Assertions.assertEquals(compilation.getPinned(), founded.getPinned());
        Assertions.assertEquals(compilation.getTitle(), founded.getTitle());
    }

    @Test
    public void testFindByPinned() {
        categoryRepository.save(category);
        userRepository.save(user);
        eventRepository.save(event);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
        List<Compilation> compList = compilationRepository.findByPinned(true);

        Assertions.assertEquals(1, compList.size());
    }
}
