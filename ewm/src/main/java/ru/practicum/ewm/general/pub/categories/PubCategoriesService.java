package ru.practicum.ewm.general.pub.categories;

import ru.practicum.ewm.models.category.CategoryDto;

import java.util.List;
import java.util.Map;

public interface PubCategoriesService {
    List<CategoryDto> getCategories(Map<String, Object> parameters);

    CategoryDto getCategoryById(Long catId);
}
