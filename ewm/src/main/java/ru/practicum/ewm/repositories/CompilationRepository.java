package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.compilation.Compilation;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    Compilation findByIdIs(Long compId);

    // Find pinned comp
    @Query(value = "select * from compilations where pinned = ?1", nativeQuery = true)
    List<Compilation> findByPinned(Boolean pinned);
}
