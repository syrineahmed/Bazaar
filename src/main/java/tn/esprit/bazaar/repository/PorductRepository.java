package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.Product;

@Repository
public interface PorductRepository extends JpaRepository<Product,Long> {
}
