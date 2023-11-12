package net.codigo.bookingsystem.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.auth.RegisterRequest;
import net.codigo.bookingsystem.base.CodigoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User-Module")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<CodigoResponse> getProfile(@RequestParam (name = "id") Long userId){
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @GetMapping("getBooking")
    public ResponseEntity<CodigoResponse> getBookingByUser(@RequestParam (name = "id") Long userId){
        return ResponseEntity.ok(userService.getBookingByUserId(userId));
    }

    @GetMapping("getPurchase")
    public ResponseEntity<CodigoResponse> getPurchaseByUser(@RequestParam (name = "id") Long userId){
        return ResponseEntity.ok(userService.getPurchaseByUserId(userId));
    }

    @PostMapping("change-password")
    public ResponseEntity<CodigoResponse> changePassword(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.changePassword(registerRequest));
    }

    @PostMapping("refundBooking")
    public ResponseEntity<CodigoResponse> refundBooking(@RequestParam (name = "user-id") Long userId,
                                                        @RequestParam (name = "purchase-id") Long purchaseId){
        return ResponseEntity.ok(userService.refundBooking(userId,purchaseId));
    }


}
