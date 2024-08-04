package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.dto.OrderedProductResponseDto;
import tn.esprit.bazaar.dto.ReviewDto;
import tn.esprit.bazaar.service.ReviewService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/orderedProduct/{orderId}")
    public ResponseEntity<OrderedProductResponseDto> getOrderedProductDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(reviewService.getOrderedProductDetailsByOrderId(orderId));
    }


    @PostMapping("/giveReview")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        try {
            ReviewDto reviewDto1 = reviewService.giveReview(reviewDto);
            if (reviewDto1 == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding review");
            }
            return ResponseEntity.status(HttpStatus.OK).body(reviewDto1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
