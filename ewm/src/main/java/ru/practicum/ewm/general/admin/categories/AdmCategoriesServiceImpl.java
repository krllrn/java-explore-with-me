package ru.practicum.ewm.general.admin.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

@Service
public class AdmCategoriesServiceImpl implements AdmCategoriesService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public AdmCategoriesServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto editCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long catId) {

    }
}
