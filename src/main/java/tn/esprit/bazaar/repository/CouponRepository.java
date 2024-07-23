package tn.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.bazaar.entities.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByCode(String code);
}
