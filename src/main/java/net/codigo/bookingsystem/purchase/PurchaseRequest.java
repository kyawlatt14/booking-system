package net.codigo.bookingsystem.purchase;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PurchaseRequest {
    private Long id;
    private String name;
    private int credits;
    private BigDecimal price;
    private String country;
    private String expirationDate;
}
