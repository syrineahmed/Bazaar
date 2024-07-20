package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.CategoryDto;
import tn.esprit.bazaar.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createcategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
