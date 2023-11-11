package net.codigo.bookingsystem.booking;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BookingDTO implements Serializable {
    private Long id;
    private long bookingTime;
    private boolean isWaitListed;
    private boolean isCheckedIn;
}
