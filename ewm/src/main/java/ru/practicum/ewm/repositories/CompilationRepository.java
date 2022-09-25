package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.compilation.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
