package hrvatski_telekom.hr.interview_assignment.controllers;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hrvatski_telekom.hr.interview_assignment.dtos.CartItemDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.OfferStatisticsDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.PurchasesDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.ShoppingCartResponse;
import hrvatski_telekom.hr.interview_assignment.services.IShoppingCartService;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {
	
	private IShoppingCartService shoppingCartService;
	
	public ShoppingCartController(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	
	@PostMapping("/create-shopping-cart")
	public ResponseEntity<ShoppingCartResponse> createShoppingCart(){
		ShoppingCartResponse shoppingCartDto = shoppingCartService.createEmptyShoppingCart();
		return new ResponseEntity<ShoppingCartResponse>(shoppingCartDto, HttpStatus.OK);
	}
	
	@GetMapping("/get-items/{shopping-cart-id}")
	public ResponseEntity<ShoppingCartResponse> getCurrentContent(@PathVariable("shopping-cart-id") Long shoppingCartId){
		
		ShoppingCartResponse shoppingCartResponse = shoppingCartService.getCurrentContent(shoppingCartId);

		return new ResponseEntity<ShoppingCartResponse>(shoppingCartResponse, HttpStatus.OK);
	}
	
	@PostMapping("add-item/{shopping-cart-id}")
	public ResponseEntity<ShoppingCartResponse> addItemToCart(
				@PathVariable("shopping-cart-id") Long shoppingCartId,
				@RequestBody CartItemDTO cartItemDto
			){
		
		ShoppingCartResponse shoppingCartResponse = shoppingCartService.addCartItem(shoppingCartId, cartItemDto);
		
		return new ResponseEntity<ShoppingCartResponse>(shoppingCartResponse, HttpStatus.OK);
	}
	
	@PostMapping("remove-cart-item/{cart-item-id}")
	public ResponseEntity<ShoppingCartResponse> removeItemFromCart(
			@PathVariable("cart-item-id") Long cartItemId
			){
		ShoppingCartResponse response = shoppingCartService.removeItemFromCart(cartItemId);
		
		return new ResponseEntity<ShoppingCartResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("evict-cart/{shopping-cart-id}")
	public ResponseEntity<String> evictCart(@PathVariable("shopping-cart-id") Long shoppingCartId) {
		
		String response = shoppingCartService.evictCart(shoppingCartId);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping("purchase-items/{shopping-cart-id}")
	public ResponseEntity<List<PurchasesDTO>> purchaseShoppingCart(@PathVariable("shopping-cart-id") Long shoppingCartId){
		List<PurchasesDTO> response = shoppingCartService.purchaseShoppingCart(shoppingCartId);
		return new ResponseEntity<List<PurchasesDTO>>(response, HttpStatus.OK);
	}
	
	@GetMapping("get-statistics")
	public ResponseEntity<List<OfferStatisticsDTO>> offerStatistics( 
			@RequestParam Long productId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime){
	
		List<OfferStatisticsDTO> response = shoppingCartService.offerStatistics(productId, startTime, endTime);
		return new ResponseEntity<List<OfferStatisticsDTO>>(response, HttpStatus.OK);
	}
}
