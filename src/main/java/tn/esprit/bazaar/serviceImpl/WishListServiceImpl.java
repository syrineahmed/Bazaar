package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.entities.WishList;
import tn.esprit.bazaar.dto.WishListDto;
import tn.esprit.bazaar.entities.Product;
import tn.esprit.bazaar.entities.User;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.repository.WishListRepository;
import tn.esprit.bazaar.service.WishListService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final WishListRepository wishListRepository;
    private final PorductRepository productRepository;
    private final UserRepository userRepository;


    //the current user can add a product to his wish list
    public WishListDto addProductToWishList(WishListDto wishListDto) {

        Optional<Product> optionalProduct = productRepository.findById(wishListDto.getProductId());
        if (optionalProduct.isPresent()) {
            WishList wishList = new WishList();
            wishList.setProduct(optionalProduct.get());


            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + userDetails.getUsername()));

            wishList.setUser(currentUser);
            return wishListRepository.save(wishList).getWishListDto();
        }
        return null;
    }

    //the current user can see his wish list
    public List<WishListDto> getWishListForCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userDetails.getUsername()));
        return wishListRepository.findAllByUserId(currentUser.getId()).stream().map(WishList::getWishListDto).collect(Collectors.toList());
    }

    //the current user can remove a product from his wish list


    public void removeProductFromWishList(Long productId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userDetails.getUsername()));
        WishList wishList = wishListRepository.findByUserIdAndProductId(currentUser.getId(), productId)
                .orElseThrow(() -> new RuntimeException("WishList not found for product id: " + productId));
        wishListRepository.delete(wishList);
    }

}
