package hrvatski_telekom.hr.interview_assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hrvatski_telekom.hr.interview_assignment.models.ItemPrice;

@Repository
public interface IItemPriceRepository extends JpaRepository<ItemPrice, Long>{

}
