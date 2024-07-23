package tn.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.bazaar.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
