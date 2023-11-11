package net.codigo.bookingsystem.booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDTO {
    private Long id;
    private long bookingTime;
    private boolean isWaitListed;
    private boolean isCheckedIn;
}
