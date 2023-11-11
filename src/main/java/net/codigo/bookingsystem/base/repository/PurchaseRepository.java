package net.codigo.bookingsystem.base.repository;

import net.codigo.bookingsystem.base.entity.Purchase;
import net.codigo.bookingsystem.base.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    Purchase findByCountryAndName(String upperCase, String name);
    List<Purchase> findByStatus(Status status);
}
