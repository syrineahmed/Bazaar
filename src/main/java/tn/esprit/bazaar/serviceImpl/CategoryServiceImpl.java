package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.CategoryDto;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.entities.Category;
import tn.esprit.bazaar.entities.Product;
import tn.esprit.bazaar.repository.CategoryRepository;
import tn.esprit.bazaar.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PorductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN')")
    //only admin can create a category
    public Category createcategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    //all users can see all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //admin can delete a category par la suite ts les produuits associes a cette categorie seront supprimés
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id) {

        List<Product> products = productRepository.findAllByCategoryId(id);
        productRepository.deleteAll(products);
        categoryRepository.deleteById(id);

    }

    //admin can update a category
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category not found with id: " + id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
    }

}
