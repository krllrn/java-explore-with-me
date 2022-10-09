package ru.practicum.ewm.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.admin.categories.AdmCategoriesServiceImpl;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.EventRepository;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase
public class AdmCategoriesServiceTest {
    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventRepository eventRepository;

    private final Category category = new Category(1L, "Category");
    private final CategoryDto categoryDto = new CategoryDto(1L, "CategoryDto");
    private final NewCategoryDto newCategoryDto = new NewCategoryDto("NewCategoryDto");

    @Test
    public void testEditCategory() {
        AdmCategoriesServiceImpl service = new AdmCategoriesServiceImpl(categoryMapper, categoryRepository, eventRepository);
        Mockito.when(categoryMapper.catDtoToEntity(any(CategoryDto.class))).thenReturn(category);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Mockito.when(categoryMapper.catEntityToDto(any(Category.class))).thenReturn(categoryDto);

        Assertions.assertEquals(categoryDto, service.editCategory(categoryDto));
    }

    @Test
    public void testAddCategory() {
        AdmCategoriesServiceImpl service = new AdmCategoriesServiceImpl(categoryMapper, categoryRepository, eventRepository);
        Mockito.when(categoryMapper.newCatDtoToEntity(any(NewCategoryDto.class))).thenReturn(category);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Mockito.when(categoryMapper.catEntityToDto(any(Category.class))).thenReturn(categoryDto);

        Assertions.assertEquals(categoryDto, service.addCategory(newCategoryDto));
    }
}
