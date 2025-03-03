package hrvatski_telekom.hr.interview_assignment.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hrvatski_telekom.hr.interview_assignment.models.CartItem;
import hrvatski_telekom.hr.interview_assignment.models.ItemPrice;
import hrvatski_telekom.hr.interview_assignment.models.Price;
import hrvatski_telekom.hr.interview_assignment.models.Purchases;
import hrvatski_telekom.hr.interview_assignment.models.ShoppingCart;

@Component
public class Mapper {

	public CartItemDTO toCartItemDTO(CartItem cartItem) {
		return new CartItemDTO(
//            cartItem.getId(),
				cartItem.getAction(), cartItem.getProductId(), cartItem.getItemPrice().getId(),
				cartItem.getItemPrice().getPrice().getValue(), cartItem.getShoppingCart().getId());
	}

	public CartItem toCartItem(CartItemDTO dto, ItemPrice itemPrice, ShoppingCart shoppingCart) {

		CartItem cartItem = new CartItem();
//		cartItem.setId(dto.id());
		cartItem.setAction(dto.action());
		cartItem.setProductId(dto.productId());
		cartItem.setItemPrice(itemPrice);
		cartItem.setShoppingCart(shoppingCart);

		return cartItem;
	}

	public ShoppingCartResponse toShoppingCartResponse(ShoppingCart shoppingCart) {
		List<CartItemDTO> cartItemDTOs = shoppingCart.getCartItems().stream().map(this::toCartItemDTO)
				.collect(Collectors.toList());

		return new ShoppingCartResponse(shoppingCart.getId(), cartItemDTOs, shoppingCart.getLastModified());
	}

	public ShoppingCart toShoppingCart(ShoppingCartRequest shoppingCartRequest, List<CartItem> cartItems) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setId(shoppingCartRequest.id());
		shoppingCart.setCartItems(cartItems);
		return shoppingCart;
	}

	public PurchasesDTO toPurchasesDTO(Purchases purchases) {

		ItemPrice itemPrice = purchases.getItemPrice();
		Price price = itemPrice.getPrice();
//		String priceType = Hibernate.getClass(price).getAnnotation(DiscriminatorValue.class).value();
		
		return new PurchasesDTO(purchases.getId(), purchases.getCustomerId(), purchases.getItemPrice().getProductId(),
				purchases.getAction(), price.getValue(), purchases.getPurchaseTime());
	}
}
