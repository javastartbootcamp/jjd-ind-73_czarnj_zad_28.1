package pl.javastart.restoffers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.javastart.restoffers.dto.CategoryIdOnlyDto;
import pl.javastart.restoffers.dto.ReadCategoryDto;
import pl.javastart.restoffers.dto.SaveCategoryDto;
import pl.javastart.restoffers.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/names")
    public List<String> getAllNames() {
        return categoryService.getAllNames();
    }

    @GetMapping
    public List<ReadCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping
    public ResponseEntity<CategoryIdOnlyDto> saveCategory(@RequestBody SaveCategoryDto category) {
        CategoryIdOnlyDto savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
}
