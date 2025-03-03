package hrvatski_telekom.hr.interview_assignment.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hrvatski_telekom.hr.interview_assignment.models.ShoppingCart;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

//	Optional<ShoppingCart> findByCustomerId(Long customerId);
	
//	@Query("SELECT new com.retail.shoppingcart.dto.CartStatistics(ci.offerId, ci.action, COUNT(ci)) " +
//	           "FROM Cart c JOIN c.items ci " +
//	           "WHERE c.createdAt BETWEEN :startDate AND :endDate " +
//	           "GROUP BY ci.offerId, ci.action")
//	    List<CartStatistics> getStatistics(LocalDateTime startDate, LocalDateTime endDate);
}
