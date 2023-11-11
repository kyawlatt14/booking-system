package net.codigo.bookingsystem.booking;

import net.codigo.bookingsystem.base.entity.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    public static BookingDTO entityToBookingDTO(Booking booking){
        return BookingDTO.builder()
                .id(booking.getId())
                .isWaitListed(booking.isWaitListed())
                .isCheckedIn(booking.isCheckedIn())
                .bookingTime(booking.getBookingTime())
                .build();
    }
    public static List<BookingDTO> entityListToBookingDTOList(List<Booking> bookings) {
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b->{
            bookingDTOS.add(entityToBookingDTO(b));
        });
        return bookingDTOS;
    }
}
