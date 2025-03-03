package hrvatski_telekom.hr.interview_assignment.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ONE_TIME")
@NoArgsConstructor
public class OneTimePrice extends Price{

	public OneTimePrice(double value) {
		super(value);
	}
}
