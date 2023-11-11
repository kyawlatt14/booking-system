package net.codigo.bookingsystem.base.entity;

import jakarta.persistence.*;
import lombok.*;
import net.codigo.bookingsystem.base.entity.enums.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "packages")
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int credits;
    private int bookingLimit;
    private BigDecimal price;
    private String country;
    private long expirationDate;
    private long createAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    List<Booking> bookings;

    public void add(Booking tempBooking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(tempBooking);
        tempBooking.setPurchase(this);
    }
}
