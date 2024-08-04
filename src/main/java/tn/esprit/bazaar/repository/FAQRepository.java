package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ,Long> {

}
