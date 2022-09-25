package ru.practicum.ewm.general.admin.categories;

import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;

public interface AdmCategoriesService {
    CategoryDto editCategory(CategoryDto categoryDto);

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);
}
