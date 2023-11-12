package net.codigo.bookingsystem.base.repository;

import net.codigo.bookingsystem.base.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    int countByPurchaseId(Long id);

    boolean existsByUserIdAndPurchaseId(Long id, Long id1);

    boolean existsByUserIdAndPurchaseIdAndIsWaitListedIsTrue(Long id, Long id1);
    List<Booking> findByUserId(Long userId);
    Optional<Booking> findByPurchaseIdAndUserId(Long purchaseId, Long userId);
    int countByPurchaseIdAndIsWaitListedFalse(Long purchaseId);
    List<Booking> findByPurchaseIdAndIsWaitListedTrueAndIsCheckedInFalse(Long purchaseId);
}
