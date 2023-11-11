package net.codigo.bookingsystem.purchase;

import lombok.Builder;
import lombok.Data;
import net.codigo.bookingsystem.base.entity.enums.Status;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@Builder
public class PurchaseDTO implements Serializable {
    private Long id;
    private String name;
    private int credits;
    private BigDecimal price;
    private String country;
    private long expirationDate;
    private long createAt;
    private Status status;
    private int bookingLimit;

}
