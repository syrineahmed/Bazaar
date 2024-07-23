package tn.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.bazaar.dto.CategoryDto;
import tn.bazaar.entities.Category;
import tn.bazaar.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;



    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createcategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting category: " + e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        try {
            categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok().body("Category updated successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating category: " + e.getMessage());
        }
    }



}
