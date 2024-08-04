package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.OrderedProductResponseDto;
import tn.esprit.bazaar.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
     OrderedProductResponseDto getOrderedProductDetailsByOrderId(Long orderId) ;
     ReviewDto giveReview(ReviewDto reviewDto) throws IOException ;


    }
