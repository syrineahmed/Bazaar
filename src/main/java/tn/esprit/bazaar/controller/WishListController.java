package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.dto.WishListDto;
import tn.esprit.bazaar.service.WishListService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProductToWishList(@RequestBody WishListDto wishListDto) {
        WishListDto postewishListDto = wishListService.addProductToWishList(wishListDto);
        if (postewishListDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding product to wishlist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postewishListDto);
    }


    @GetMapping("/mywishlist")
    public ResponseEntity<List<WishListDto>> getWishListForCurrentUser() {
        List<WishListDto> wishList = wishListService.getWishListForCurrentUser();
        return ResponseEntity.ok(wishList);
    }

    @DeleteMapping("/removeproduct/{productId}")
    public ResponseEntity<?> removeProductFromWishList(@PathVariable Long productId) {
        try {
            wishListService.removeProductFromWishList(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Product removed from wishlist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing product from wishlist: " + e.getMessage());
        }
    }
}
