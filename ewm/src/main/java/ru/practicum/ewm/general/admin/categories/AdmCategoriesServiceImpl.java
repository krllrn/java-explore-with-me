package ru.practicum.ewm.general.admin.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ConflictHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.EventRepository;

@Service
public class AdmCategoriesServiceImpl implements AdmCategoriesService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public AdmCategoriesServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    /*
    Обратите внимание: имя категории должно быть уникальным
    */
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

    /*
    Обратите внимание: с категорией не должно быть связано ни одного события.
    */
    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.findEventsByCategory(catId).size() > 0) {
            throw new ConflictHandler("Some events linked with category.");
        }
        categoryRepository.delete(categoryRepository.findByIdIs(catId));
    }
}
