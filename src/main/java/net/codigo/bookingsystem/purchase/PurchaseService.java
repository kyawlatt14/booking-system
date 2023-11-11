package net.codigo.bookingsystem.purchase;

import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.base.CodigoResponse;
import net.codigo.bookingsystem.base.Constant;
import net.codigo.bookingsystem.base.entity.Purchase;
import net.codigo.bookingsystem.base.entity.enums.Status;
import net.codigo.bookingsystem.base.exception.ApplicationErrorException;
import net.codigo.bookingsystem.base.repository.PurchaseRepository;
import net.codigo.bookingsystem.base.utils.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static net.codigo.bookingsystem.purchase.PurchaseMapper.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @CacheEvict(value = "purchaseList", key = "'all'")
    public CodigoResponse createPurchase(PurchaseRequest purchaseRequest) {
        var existed = purchaseRepository.findByCountryAndName(purchaseRequest.getCountry().toUpperCase(),purchaseRequest.getName().toUpperCase());
        Purchase purchase ;
        if(ObjectUtils.isEmpty(existed))
            purchase =  purchaseRepository.save(requestToEntityPurchase(purchaseRequest));
        else
            throw new ApplicationErrorException(Constant.PURCHASE_EXISTED);
        return CodigoResponse.success(Constant.PURCHASE_SAVED,entityToPurchaseDTO(purchase));
    }

    @Cacheable(value = "purchaseList", key = "'all'")
    public CodigoResponse getAll() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return CodigoResponse.success(Constant.GET_ALL,entityListToPurchaseDTOList(purchases));
    }


    @CacheEvict(value = "purchaseList", key = "'all'")
    public CodigoResponse updatePurchase(PurchaseRequest purchaseRequest) {
        var existed = purchaseRepository.findById(purchaseRequest.getId()).orElseThrow(
                ()->new ApplicationErrorException(Constant.PURCHASE_NOT_FOUND)
        );
        existed.setName(ObjectUtils.isEmpty(purchaseRequest.getName())?existed.getName():purchaseRequest.getName());
        existed.setCountry(ObjectUtils.isEmpty(purchaseRequest.getCountry())?existed.getCountry():purchaseRequest.getCountry());
        existed.setCredits(ObjectUtils.isEmpty(purchaseRequest.getCredits())?existed.getCredits():purchaseRequest.getCredits());
        existed.setPrice(ObjectUtils.isEmpty(purchaseRequest.getPrice())?existed.getPrice():purchaseRequest.getPrice());
        existed.setExpirationDate(ObjectUtils.isEmpty(purchaseRequest.getExpirationDate())?existed.getExpirationDate(): DateUtils.stringToLongDate(purchaseRequest.getExpirationDate()));
        return CodigoResponse.success(Constant.PURCHASE_UPDATED,entityToPurchaseDTO(existed));
    }

    public String findByStatus(Status status) {
        var purchases = purchaseRepository.findByStatus(status);
        purchases.forEach(purchase -> {
            if(purchase.getExpirationDate()<DateUtils.getNowDate()){
                purchase.setStatus(Status.EXPIRED);
                purchaseRepository.save(purchase);
            }
        });
        return "Expired PurchaseList is running.....";
    }
}
