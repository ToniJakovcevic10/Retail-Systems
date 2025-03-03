package hrvatski_telekom.hr.interview_assignment.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hrvatski_telekom.hr.interview_assignment.dtos.OfferStatisticsDTO;
import hrvatski_telekom.hr.interview_assignment.models.Purchases;

public interface IPurchasesRepository extends JpaRepository<Purchases, Long>{
	

	 @Query("SELECT new hrvatski_telekom.hr.interview_assignment.dtos.OfferStatisticsDTO(ip.productId, p.action, COUNT(p), :startTime, :endTime) " +
	           "FROM Purchases p " +
	           "JOIN p.itemPrice ip " +
	           "WHERE ip.productId = :productId " +
	           "AND p.purchaseTime BETWEEN :startTime AND :endTime " +
	           "GROUP BY ip.productId, p.action " +
	           "ORDER BY ip.productId, p.action")
	    List<OfferStatisticsDTO> findOfferStatisticsByProductAndPeriod(@Param("productId") Long productId,
	                                                                   @Param("startTime") LocalDateTime startTime,
	                                                                   @Param("endTime") LocalDateTime endTime);
}
