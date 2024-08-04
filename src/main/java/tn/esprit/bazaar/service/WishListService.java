package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.WishListDto;

import java.util.List;

public interface WishListService {
    WishListDto addProductToWishList(WishListDto wishListDto);
    List<WishListDto> getWishListForCurrentUser();
    public void removeProductFromWishList(Long productId) ;

    }
