package ru.practicum.ewm.general.pub.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PubCategoriesServiceImpl implements PubCategoriesService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public PubCategoriesServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::catEntityToDto)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        if (catId <= 0) {
            throw new ForbiddenHandler("Category ID can't be less than 1.");
        }
        return categoryMapper.catEntityToDto(categoryRepository.findByIdIs(catId));
    }
}
