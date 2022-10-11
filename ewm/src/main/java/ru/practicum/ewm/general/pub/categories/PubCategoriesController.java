package ru.practicum.ewm.general.pub.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.category.CategoryDto;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
public class PubCategoriesController {
    private final PubCategoriesService pubCategoriesService;

    @Autowired
    public PubCategoriesController(PubCategoriesService pubCategoriesService) {
        this.pubCategoriesService = pubCategoriesService;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        log.info("Get categories with from&size: {}, {}", from, size);
        return pubCategoriesService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("Get category by id: {}", catId);
        return pubCategoriesService.getCategoryById(catId);
    }
}
