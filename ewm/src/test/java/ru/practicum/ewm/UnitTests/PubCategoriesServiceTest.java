package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.pub.categories.PubCategoriesServiceImpl;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class PubCategoriesServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    private final Category category = new Category(1L, "Title");
    private final CategoryDto categoryDto = new CategoryDto(1L, "CategoryDto");

    @Test
    public void testGetCategoryById() {
        PubCategoriesServiceImpl pubCategoriesService = new PubCategoriesServiceImpl(categoryRepository, categoryMapper);
        Mockito.when(categoryRepository.findByIdIs(any(Long.class))).thenReturn(category);
        Mockito.when(categoryMapper.catEntityToDto(any(Category.class))).thenReturn(categoryDto);

        CategoryDto founded = pubCategoriesService.getCategoryById(1L);

        Assertions.assertEquals(categoryDto.getName(), founded.getName());
    }
}
