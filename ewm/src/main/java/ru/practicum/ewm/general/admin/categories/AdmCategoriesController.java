package ru.practicum.ewm.general.admin.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.category.CategoryDto;
import ru.practicum.ewm.models.category.NewCategoryDto;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
public class AdmCategoriesController {

    private final AdmCategoriesService admCategoriesService;

    @Autowired
    public AdmCategoriesController(AdmCategoriesService admCategoriesService) {
        this.admCategoriesService = admCategoriesService;
    }

    /*
    Обратите внимание: имя категории должно быть уникальным
     */
    @PatchMapping
    public CategoryDto editCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Edit category with name: {}", categoryDto.getName());
        return admCategoriesService.editCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("Add new category with name: {}", newCategoryDto.getName());
        return admCategoriesService.addCategory(newCategoryDto);
    }

    /*
    Обратите внимание: с категорией не должно быть связано ни одного события.
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long catId){
        log.info("Delete category with ID: {}", catId);
        admCategoriesService.deleteCategory(catId);
    }
}
