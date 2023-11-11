package net.codigo.bookingsystem.purchase;

import net.codigo.bookingsystem.base.entity.Purchase;
import net.codigo.bookingsystem.base.entity.enums.Status;
import net.codigo.bookingsystem.base.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class PurchaseMapper {
    public static PurchaseDTO entityToPurchaseDTO(Purchase purchase){
        return PurchaseDTO.builder()
                .id(purchase.getId())
                .name(purchase.getName().toUpperCase())
                .country(purchase.getCountry().toUpperCase())
                .credits(purchase.getCredits())
                .expirationDate(purchase.getExpirationDate())
                .price(purchase.getPrice())
                .createAt(purchase.getCreateAt())
                .bookingLimit(purchase.getBookingLimit())
                .status(purchase.getStatus())
                .build();
    }

    public static Purchase requestToEntityPurchase(PurchaseRequest purchaseRequest){
        return Purchase.builder()
                .name(purchaseRequest.getName().toUpperCase())
                .country(purchaseRequest.getCountry().toUpperCase())
                .credits(purchaseRequest.getCredits())
                .expirationDate(DateUtils.stringToLongDate(purchaseRequest.getExpirationDate()))
                .price(purchaseRequest.getPrice())
                .createAt(DateUtils.getNowDate())
                .bookingLimit(5)
                .status(Status.STILL)
                .build();
    }

    public static List<PurchaseDTO> entityListToPurchaseDTOList(List<Purchase> purchases){
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        purchases.forEach(purchase -> purchaseDTOS.add(entityToPurchaseDTO(purchase)));
        return purchaseDTOS;
    }


}
