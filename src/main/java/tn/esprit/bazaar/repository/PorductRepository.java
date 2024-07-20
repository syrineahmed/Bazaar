package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.Product;

import java.util.List;

@Repository
public interface PorductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByNameContaining(String title);
    List<Product> findAllByCategoryId(Long categoryId);
}
