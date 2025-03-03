package hrvatski_telekom.hr.interview_assignment.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ShoppingCartResponse(
	    Long id,
	    List<CartItemDTO> cartItems,
	    LocalDateTime lastModified
	) {}
