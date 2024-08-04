package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.FAQ;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ,Long> {
    List<FAQ> findAllByProductId(Long productId);

}
