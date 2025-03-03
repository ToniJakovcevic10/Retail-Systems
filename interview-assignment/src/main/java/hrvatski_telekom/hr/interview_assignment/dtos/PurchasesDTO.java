package hrvatski_telekom.hr.interview_assignment.dtos;

import java.time.LocalDateTime;

import hrvatski_telekom.hr.interview_assignment.models.ActionType;

public record PurchasesDTO(
	    Long id,
	    Long customerId,
	    Long productId,
	    ActionType actionType, 
	    double priceValue,
	    LocalDateTime purchaseTime
	) {}
