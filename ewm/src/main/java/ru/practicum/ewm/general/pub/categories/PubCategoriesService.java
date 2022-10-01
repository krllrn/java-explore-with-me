package ru.practicum.ewm.general.pub.categories;

import ru.practicum.ewm.models.category.CategoryDto;

import java.util.List;
import java.util.Map;

public interface PubCategoriesService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
