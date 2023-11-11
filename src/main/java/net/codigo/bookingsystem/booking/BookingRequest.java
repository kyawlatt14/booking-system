package net.codigo.bookingsystem.booking;

import lombok.Builder;
import lombok.Data;
import net.codigo.bookingsystem.purchase.PurchaseDTO;
import net.codigo.bookingsystem.user.UserDTO;

@Data
@Builder
public class BookingRequest {
    private UserDTO userDTO;
    private PurchaseDTO purchaseDTO;
    private long bookingTime;
    private boolean isWaitListed;
    private boolean isCheckedIn;
}
