package ru.practicum.ewm.general.pub.categories;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

import java.util.List;
import java.util.Map;

public class PubCategoriesServiceImpl implements PubCategoriesService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public PubCategoriesServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return null;
    }
}
