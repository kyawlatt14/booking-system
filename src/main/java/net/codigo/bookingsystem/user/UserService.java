package net.codigo.bookingsystem.user;

import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.auth.RegisterRequest;
import net.codigo.bookingsystem.base.CodigoResponse;
import net.codigo.bookingsystem.base.Constant;
import net.codigo.bookingsystem.base.entity.Booking;
import net.codigo.bookingsystem.base.entity.Purchase;
import net.codigo.bookingsystem.base.exception.ApplicationErrorException;
import net.codigo.bookingsystem.base.repository.BookingRepository;
import net.codigo.bookingsystem.base.repository.PurchaseRepository;
import net.codigo.bookingsystem.base.repository.UserRepository;
import net.codigo.bookingsystem.base.utils.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static net.codigo.bookingsystem.purchase.PurchaseMapper.entityListToPurchaseDTOList;
import static net.codigo.bookingsystem.user.UserMapper.entityToUserDTO;
import static net.codigo.bookingsystem.user.UserMapper.entityToUserDTOWith;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;
    private final PurchaseRepository purchaseRepository;

    public CodigoResponse getProfile(Long userId) {
        var existedUser = userRepository.findById(userId).orElseThrow(
                ()->new ApplicationErrorException(Constant.USER_NOT_FOUND)
        );
        return CodigoResponse.success(Constant.USER_FETCHED,entityToUserDTO(existedUser));
    }

    public CodigoResponse changePassword(RegisterRequest registerRequest) {
        var existedUser = userRepository.findById(registerRequest.getId()).orElseThrow(
                ()->new ApplicationErrorException(Constant.USER_NOT_FOUND)
        );
        if(passwordEncoder.matches(registerRequest.getPassword(),existedUser.getPassword())){
            existedUser.setPassword(passwordEncoder.encode(registerRequest.getNewPassword()));
            userRepository.save(existedUser);
        }else
            throw new ApplicationErrorException(Constant.PASSWORD_NOT_MATCH);
        return CodigoResponse.success(Constant.USER_PASSWORD_UPDATED,entityToUserDTO(existedUser));
    }

    public CodigoResponse getBookingByUserId(Long userId) {
        var existedUser = userRepository.findById(userId).orElseThrow(
                ()->new ApplicationErrorException(Constant.USER_NOT_FOUND)
        );
        return CodigoResponse.success(Constant.USER_FETCHED,entityToUserDTOWith(existedUser));
    }

    public CodigoResponse getPurchaseByUserId(Long userId) {
        var bookingList = bookingRepository.findByUserId(userId);
        List<Purchase> list = new ArrayList<>();
        bookingList.forEach(b-> list.add(b.getPurchase()));
        return CodigoResponse.success(Constant.USER_FETCHED,entityListToPurchaseDTOList(list));
    }

    public CodigoResponse refundBooking(Long userId, Long purchaseId) {
        var booking = bookingRepository.findByPurchaseIdAndUserId(purchaseId,userId).orElseThrow();
        if(booking.getPurchase().getExpirationDate() > DateUtils.getNowDate()){
            booking.setCheckedIn(true);
            booking.setWaitListed(true);
            bookingRepository.save(booking);
        }
        boolean flat = PaymentCharge(booking.isWaitListed(),booking.isCheckedIn());
        if(!flat){
            var addBooking = bookingRepository.findByPurchaseIdAndIsWaitListedTrueAndIsCheckedInFalse(purchaseId);
            for(Booking booking1 :addBooking){
                if(booking.getPurchase().getBookingLimit() > bookingRepository.countByPurchaseIdAndIsWaitListedFalse(purchaseId)){
                    booking1.setWaitListed(false);
                    booking1.setCheckedIn(false);
                    booking1.setDepositCredits(1);
                }
            }
        }
        return CodigoResponse.success(flat?Constant.REFUND_PAID:Constant.REFUND_NO_PAID,null);
    }

    public boolean PaymentCharge(boolean a,boolean b){
        if(a&b)
            return false;
        return true;
    }
}
