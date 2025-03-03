package hrvatski_telekom.hr.interview_assignment.dtos;

import java.time.LocalDateTime;

import hrvatski_telekom.hr.interview_assignment.models.ActionType;

public record OfferStatisticsDTO(
	    Long productId,
	    ActionType action,
	    Long count,
	    LocalDateTime startTime,
	    LocalDateTime endTime
	) {}
