package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;

@Component
public class CategoryMapper {
    @Autowired
    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Category catDtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto catEntityToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category newCatDtoToEntity(NewCategoryDto newCategoryDto) {
        return modelMapper.map(newCategoryDto, Category.class);
    }
}
