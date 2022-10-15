package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdIs(Long userId);
}
