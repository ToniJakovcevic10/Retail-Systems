package hrvatski_telekom.hr.interview_assignment.dtos;

public record PriceDTO(
		Long id,
		String priceType,
	    double value,
	    Integer numberOfRecurrences) {

}
