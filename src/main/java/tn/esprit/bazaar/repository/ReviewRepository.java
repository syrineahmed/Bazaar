package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
