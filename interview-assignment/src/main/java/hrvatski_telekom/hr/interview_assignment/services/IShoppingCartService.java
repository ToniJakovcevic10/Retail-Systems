package hrvatski_telekom.hr.interview_assignment.services;

import java.time.LocalDateTime;
import java.util.List;

import hrvatski_telekom.hr.interview_assignment.dtos.CartItemDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.OfferStatisticsDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.PurchasesDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.ShoppingCartResponse;

public interface IShoppingCartService {
	ShoppingCartResponse createEmptyShoppingCart();
	ShoppingCartResponse getCurrentContent(Long shoppingCartId);
	ShoppingCartResponse addCartItem(Long shoppingCartId, CartItemDTO itemDto);
	ShoppingCartResponse removeItemFromCart(Long cartItemId);
	String evictCart(Long shoppingCartId);
	List<PurchasesDTO> purchaseShoppingCart(Long shoppingCartId);
	List<OfferStatisticsDTO> offerStatistics(Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
