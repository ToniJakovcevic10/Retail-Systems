package hrvatski_telekom.hr.interview_assignment.dtos;

import hrvatski_telekom.hr.interview_assignment.models.ActionType;

public record CartItemDTO(
	    ActionType action,
	    Long productId,
	    Long itemPriceId,
	    double itemPriceAmount,
	    Long shoppingCartId
	) {}
