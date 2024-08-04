package tn.esprit.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.bazaar.entities.WishList;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUserId(Long userId);
    Optional<WishList> findByUserIdAndProductId(Long userId, Long productId);

}
