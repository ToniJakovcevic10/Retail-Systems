package hrvatski_telekom.hr.interview_assignment.dtos;

import java.util.List;

public record ShoppingCartRequest(
	    Long id,
	    List<Long> cartItemIds
	) {}
