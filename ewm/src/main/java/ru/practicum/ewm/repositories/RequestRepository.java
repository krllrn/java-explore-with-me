package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.request.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
