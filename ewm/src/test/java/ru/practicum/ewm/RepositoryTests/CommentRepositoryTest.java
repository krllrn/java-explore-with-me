package ru.practicum.ewm.RepositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CommentRepositoryTest {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final CommentMapper commentMapper = new CommentMapper(new ModelMapper());

    private final CommentShortDto commentDto = new CommentShortDto("Comment");
    private final Category category = new Category("Category");
    private final User user = new User("test@email.ru", "Name");
    private final Event event = new Event("Annotation", category, LocalDateTime.now(), "Description", LocalDateTime.now().plusDays(4),
            user, new Location(), false, 15, true, "Title");

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository, EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByIdIs() {
        categoryRepository.save(category);
        userRepository.save(user);
        eventRepository.save(event);
        Comment comment = commentMapper.shortToEntity("Troll", event, commentDto);
        commentRepository.save(comment);

        Comment founded = commentRepository.findByIdIs(comment.getId());

        Assertions.assertEquals(comment.getAuthorName(), founded.getAuthorName());
        Assertions.assertEquals(comment.getText(), founded.getText());
    }
}
