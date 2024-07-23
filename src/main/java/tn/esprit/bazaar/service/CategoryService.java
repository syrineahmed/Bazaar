package tn.bazaar.service;

import tn.bazaar.dto.CategoryDto;
import tn.bazaar.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createcategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
     void deleteCategory(Long id) ;
    public void updateCategory(Long id, CategoryDto categoryDto) ;


    }
