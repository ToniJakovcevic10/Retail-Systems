package hrvatski_telekom.hr.interview_assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hrvatski_telekom.hr.interview_assignment.models.CartItem;
import jakarta.transaction.Transactional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.shoppingCart.id = :shoppingCartId")
	void deleteByShoppingCartId(Long shoppingCartId);
}
