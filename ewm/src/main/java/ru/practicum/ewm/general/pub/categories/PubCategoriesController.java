package ru.practicum.ewm.general.pub.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.category.CategoryDto;

import java.util.List;
import java.util.Map;

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
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        log.info("Get categories with parameters: {}", parameters.values());
        return pubCategoriesService.getCategories(parameters);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("Get category by id: {}", catId);
        return pubCategoriesService.getCategoryById(catId);
    }
}
