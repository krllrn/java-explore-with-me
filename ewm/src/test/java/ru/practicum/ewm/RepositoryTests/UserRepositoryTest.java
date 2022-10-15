package ru.practicum.ewm.RepositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.repositories.UserRepository;

import javax.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTest {
    private final UserRepository userRepository;

    private final User user = new User("test@email.ru", "Name");

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByIdIs() {
        userRepository.save(user);
        User founded = userRepository.findByIdIs(user.getId());

        Assertions.assertEquals(user.getEmail(), founded.getEmail());
        Assertions.assertEquals(user.getName(), founded.getName());
    }
}
