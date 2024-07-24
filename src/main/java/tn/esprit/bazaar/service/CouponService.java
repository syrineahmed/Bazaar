package tn.esprit.bazaar.service;

import tn.esprit.bazaar.entities.Coupon;

import java.util.List;

public interface CouponService {
 Coupon createCoupon(Coupon coupon) ;
 List<Coupon> getAllCoupons() ;


    }
