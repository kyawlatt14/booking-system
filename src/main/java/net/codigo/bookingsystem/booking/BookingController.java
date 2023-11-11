package net.codigo.bookingsystem.booking;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.base.CodigoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Tag(name = "Booking-Module")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<CodigoResponse> createBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }
}
