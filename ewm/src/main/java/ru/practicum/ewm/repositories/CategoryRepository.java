package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
