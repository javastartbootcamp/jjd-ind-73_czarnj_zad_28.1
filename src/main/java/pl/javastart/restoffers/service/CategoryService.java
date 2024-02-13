package pl.javastart.restoffers.service;

import org.springframework.stereotype.Service;
import pl.javastart.restoffers.dto.CategoryIdOnlyDto;
import pl.javastart.restoffers.dto.ReadCategoryDto;
import pl.javastart.restoffers.dto.SaveCategoryDto;
import pl.javastart.restoffers.entity.Category;
import pl.javastart.restoffers.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllNames() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(Category::getName)
                .toList();
    }

    public Category findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    public List<ReadCategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryService.mapToReadCategoryDto(category,
                        categoryRepository.countOffersForCategoryId(category.getId())))
                .toList();
    }

    private static ReadCategoryDto mapToReadCategoryDto(Category category, int offersAmount) {
        return new ReadCategoryDto(category.getName(), category.getDescription(), offersAmount);
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryIdOnlyDto saveCategory(SaveCategoryDto category) {
        Category categoryToSave = mapToCategory(category);
        categoryToSave = categoryRepository.save(categoryToSave);
        return new CategoryIdOnlyDto(categoryToSave.getId());
    }

    private Category mapToCategory(SaveCategoryDto category) {
        Category categoryToSave = new Category();
        categoryToSave.setName(category.name());
        categoryToSave.setDescription(category.description());
        return categoryToSave;
    }
}
