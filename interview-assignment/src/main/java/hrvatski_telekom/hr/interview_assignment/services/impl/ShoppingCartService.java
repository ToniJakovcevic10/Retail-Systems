package hrvatski_telekom.hr.interview_assignment.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import hrvatski_telekom.hr.interview_assignment.dtos.CartItemDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.Mapper;
import hrvatski_telekom.hr.interview_assignment.dtos.OfferStatisticsDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.PurchasesDTO;
import hrvatski_telekom.hr.interview_assignment.dtos.ShoppingCartResponse;
import hrvatski_telekom.hr.interview_assignment.exceptions.BadRequestException;
import hrvatski_telekom.hr.interview_assignment.exceptions.InternalServerErrorException;
import hrvatski_telekom.hr.interview_assignment.exceptions.ResourceNotFoundException;
import hrvatski_telekom.hr.interview_assignment.models.CartItem;
import hrvatski_telekom.hr.interview_assignment.models.ItemPrice;
import hrvatski_telekom.hr.interview_assignment.models.Purchases;
import hrvatski_telekom.hr.interview_assignment.models.ShoppingCart;
import hrvatski_telekom.hr.interview_assignment.repositories.ICartItemRepository;
import hrvatski_telekom.hr.interview_assignment.repositories.IItemPriceRepository;
import hrvatski_telekom.hr.interview_assignment.repositories.IPurchasesRepository;
import hrvatski_telekom.hr.interview_assignment.repositories.IShoppingCartRepository;
import hrvatski_telekom.hr.interview_assignment.services.IShoppingCartService;

@Service
public class ShoppingCartService implements IShoppingCartService {

	private IShoppingCartRepository shoppingCartRepository;
	private IItemPriceRepository itemPriceRepository;
	private ICartItemRepository cartItemRepository;
	private IPurchasesRepository purchasesRepository;
	private Mapper mapper;

	public ShoppingCartService(IShoppingCartRepository shoppingCartRepository, ICartItemRepository cartItemRepository,
			IItemPriceRepository itemPriceRepository, Mapper mapper, IPurchasesRepository purchasesRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
		this.cartItemRepository = cartItemRepository;
		this.itemPriceRepository = itemPriceRepository;
		this.purchasesRepository = purchasesRepository;
		this.mapper = mapper;
	}

	@Override
	public ShoppingCartResponse createEmptyShoppingCart() throws InternalServerError {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCartItems(new ArrayList<CartItem>());
		shoppingCartRepository.save(shoppingCart);

		return mapper.toShoppingCartResponse(shoppingCart);
	}

	public CartItem saveCartItem(CartItemDTO itemDto) throws InternalServerError {

		ShoppingCart shoppingCart = shoppingCartRepository.findById(itemDto.shoppingCartId())
				.orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for itemDto: " + itemDto));

		ItemPrice itemPrice = itemPriceRepository.findById(itemDto.itemPriceId()).orElseThrow(
				() -> new ResourceNotFoundException("ItemPrice not found for ID: " + itemDto.itemPriceId()));

		CartItem item = mapper.toCartItem(itemDto, itemPrice, shoppingCart);
		cartItemRepository.save(item);
		return item;
	}

	@Override
	public ShoppingCartResponse getCurrentContent(Long shoppingCartId) throws InternalServerError {

		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
				.orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));

		ShoppingCartResponse shoppingCartDTO = mapper.toShoppingCartResponse(shoppingCart);

		return shoppingCartDTO;
	}

	@Override
	public ShoppingCartResponse addCartItem(Long shoppingCartId, CartItemDTO itemDto) throws BadRequestException {

		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
				.orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));

		ItemPrice itemPrice = itemPriceRepository.findById(itemDto.itemPriceId())
				.orElseThrow(() -> new ResourceNotFoundException("CartItem not found."));

		CartItem item = mapper.toCartItem(itemDto, itemPrice, shoppingCart);

		List<CartItem> items = shoppingCart.getCartItems();
		items.add(item);
		try {
			shoppingCart.setCartItems(items);
		} catch (Exception e) {
			throw new BadRequestException("Item is not added to cart. Try again!");
		}

		ShoppingCartResponse response = mapper.toShoppingCartResponse(shoppingCart);

		return response;
	}

	@Override
	public ShoppingCartResponse removeItemFromCart(Long cartItemId) throws BadRequestException {

		CartItem itemToRemove = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem not found."));

		try {
			cartItemRepository.delete(itemToRemove);
		} catch (Exception e) {
			throw new BadRequestException("Couldn't remove item from shoppingCart: " + cartItemId);
		}

		ShoppingCart shoppingCart = shoppingCartRepository.findById(itemToRemove.getShoppingCart().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Shopping cart with id: " + itemToRemove.getShoppingCart().getId() + " is not found."));

		ShoppingCartResponse dto = mapper.toShoppingCartResponse(shoppingCart);

		return dto;
	}

	@Override
	public String evictCart(Long shoppingCartId) throws RuntimeErrorException {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(
				() -> new ResourceNotFoundException("Shopping cart with id: " + shoppingCartId + " is not found."));

		if (shoppingCart.getCartItems().isEmpty()) {
			throw new Error("Shopping cart is already empty.");
		} else {
			try {
				cartItemRepository.deleteByShoppingCartId(shoppingCartId);
			} catch (Exception e) {
				throw new InternalServerErrorException("Shopping cart is not evicted.");
			}
		}

		return "ShoppingCart with id: " + shoppingCartId + " is successfully evicted!";
	}

	@Override
	public List<PurchasesDTO> purchaseShoppingCart(Long shoppingCartId) {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(
				() -> new ResourceNotFoundException("Shopping cart with id: " + shoppingCartId + " is not found."));
		if (shoppingCart.getCartItems().isEmpty()) {
			throw new BadRequestException("There are no items in the cart.");
		}

		List<PurchasesDTO> purchases = new ArrayList<PurchasesDTO>();
		try {

			for (CartItem cartItem : shoppingCart.getCartItems()) {

				Purchases purchaseItem = new Purchases();
				purchaseItem.setPurchaseTime(LocalDateTime.now());
				purchaseItem.setCustomerId(shoppingCart.getCustomerId());
				purchaseItem.setItemPrice(cartItem.getItemPrice());

				purchasesRepository.save(purchaseItem);

				purchases.add(mapper.toPurchasesDTO(purchaseItem));
			}
			shoppingCartRepository.delete(shoppingCart);
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}

		return purchases;

	}

	@Override
	public List<OfferStatisticsDTO> offerStatistics(Long productId, LocalDateTime startDate, LocalDateTime endDate) {
		try {
			return purchasesRepository.findOfferStatisticsByProductAndPeriod(productId, startDate, endDate);
		}catch (Exception e) {
			throw new BadRequestException("error while getting statistics for " + productId);
		}
	}

}
