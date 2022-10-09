package ru.practicum.ewm.RepositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

import javax.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CategoryRepositoryTest {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper = new CategoryMapper(new ModelMapper());

    private final CategoryDto categoryDto = new CategoryDto("Category");

    @Autowired
    public CategoryRepositoryTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    public void testFindById() {
        Category category = categoryMapper.catDtoToEntity(categoryDto);
        categoryRepository.save(category);
        Category founded = categoryRepository.findByIdIs(category.getId());

        Assertions.assertEquals(category.getName(), founded.getName());
        Assertions.assertEquals(category.getId(), founded.getId());
    }
}
