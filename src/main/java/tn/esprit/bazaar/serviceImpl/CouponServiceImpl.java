package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.exceptions.ValidationException;
import tn.esprit.bazaar.entities.Coupon;
import tn.esprit.bazaar.repository.CouponRepository;
import tn.esprit.bazaar.service.CouponService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
   private final CouponRepository couponRepository;

   //admin can create a coupon
    @PreAuthorize("hasRole('ADMIN')")
   public Coupon createCoupon(Coupon coupon) {
       if (couponRepository.existsByCode(coupon.getCode())) {
           throw new ValidationException("coupon code already exists");
       }
       return couponRepository.save(coupon);
   }

   //admin can see all coupons
   @PreAuthorize("hasRole('ADMIN')")
   public List<Coupon> getAllCoupons() {
       return couponRepository.findAll();
   }
}
