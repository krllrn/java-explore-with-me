package ru.practicum.ewm.general.pub.categories;

import ru.practicum.ewm.models.category.CategoryDto;

import java.util.List;

public interface PubCategoriesService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
