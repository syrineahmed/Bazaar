package tn.bazaar.service;

import tn.bazaar.entities.Coupon;

import java.util.List;

public interface CouponService {
 Coupon createCoupon(Coupon coupon) ;
 List<Coupon> getAllCoupons() ;


    }
