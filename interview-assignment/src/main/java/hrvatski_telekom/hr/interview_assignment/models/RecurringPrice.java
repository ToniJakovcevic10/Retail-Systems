package hrvatski_telekom.hr.interview_assignment.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("RECURRING")
@NoArgsConstructor
public class RecurringPrice extends Price{

	private int numberOfRecurrences;
	
	public RecurringPrice(double value, int numberOfReccurences) {
		super(value);
		this.setNumberOfRecurrences(numberOfReccurences);
	}

	public int getNumberOfReccurences() {
		return numberOfRecurrences;
	}

	public void setNumberOfRecurrences(int numberOfReccurences) {
		this.numberOfRecurrences = numberOfReccurences;
	}
	
}
