package ru.practicum.ewm.general.admin.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ConflictHandler;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.EventRepository;

@Service
@RequiredArgsConstructor
public class AdmCategoriesServiceImpl implements AdmCategoriesService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto editCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.catDtoToEntity(categoryDto);
        return categoryMapper.catEntityToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.newCatDtoToEntity(newCategoryDto);
        return categoryMapper.catEntityToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.findEventsByCategory(catId).size() > 0) {
            throw new ConflictHandler("Some events linked with category.");
        }
        categoryRepository.delete(categoryRepository.findByIdIs(catId));
    }
}
