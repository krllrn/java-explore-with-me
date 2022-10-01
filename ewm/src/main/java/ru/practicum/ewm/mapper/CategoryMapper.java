package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;
import ru.practicum.ewm.repositories.CategoryRepository;

@Component
public class CategoryMapper {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final CategoryRepository categoryRepository;


    public CategoryMapper(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
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
