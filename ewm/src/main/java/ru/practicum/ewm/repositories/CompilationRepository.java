package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.compilation.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    // Delete comp by id
    @Query(value = "delete * from compilations where id = ?1")
    void deleteById(Long compId);

    //Get comp by id
    @Query(value = "select * from compilations where id = ?1")
    Compilation getCompById(Long compId);
}
