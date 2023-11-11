package net.codigo.bookingsystem.purchase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.base.CodigoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
@Tag(name = "Purchase-Module")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CodigoResponse> createPurchase(@RequestBody PurchaseRequest purchaseRequest){
        return ResponseEntity.ok(purchaseService.createPurchase(purchaseRequest));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CodigoResponse> updatePurchase(@RequestBody PurchaseRequest purchaseRequest){
        return ResponseEntity.ok(purchaseService.updatePurchase(purchaseRequest));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:create','user:create')")
    public ResponseEntity<CodigoResponse> getAll(){
        return ResponseEntity.ok(purchaseService.getAll());
    }
}
