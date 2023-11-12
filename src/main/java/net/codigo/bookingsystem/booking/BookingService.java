package net.codigo.bookingsystem.booking;

import lombok.RequiredArgsConstructor;
import net.codigo.bookingsystem.base.CodigoResponse;
import net.codigo.bookingsystem.base.Constant;
import net.codigo.bookingsystem.base.entity.Booking;
import net.codigo.bookingsystem.base.exception.ApplicationErrorException;
import net.codigo.bookingsystem.base.repository.BookingRepository;
import net.codigo.bookingsystem.base.repository.PurchaseRepository;
import net.codigo.bookingsystem.base.repository.UserRepository;
import net.codigo.bookingsystem.base.utils.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.codigo.bookingsystem.booking.BookingMapper.entityListToBookingDTOList;
import static net.codigo.bookingsystem.booking.BookingMapper.entityToBookingDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    @CacheEvict(value = "bookings", key = "'all'")
    public CodigoResponse createBooking(BookingRequest bookingRequest) {
        Booking booking;
        if(bookingRequest.getPurchaseDTO().getExpirationDate()> DateUtils.getNowDate() &&
                bookingRequest.getUserDTO().getCountry().equals(bookingRequest.getPurchaseDTO().getCountry())){
            if(bookingRequest.getPurchaseDTO().getBookingLimit() > bookingRepository.countByPurchaseId(bookingRequest.getPurchaseDTO().getId()))
                booking = saveBooking(bookingRequest);
            else
                booking = waitBooking(bookingRequest);
        }else
            throw new ApplicationErrorException(Constant.USER_NOT_ALLOW);
        return CodigoResponse.success(!booking.isWaitListed()?Constant.BOOKING_SAVE:Constant.BOOKING_WAIT,
                entityToBookingDTO(booking));
    }

    private Booking waitBooking(BookingRequest bookingRequest) {
        var user = userRepository.findById(bookingRequest.getUserDTO().getId()).orElseThrow();
        var purchase = purchaseRepository.findById(bookingRequest.getPurchaseDTO().getId()).orElseThrow();
        if(bookingRepository.existsByUserIdAndPurchaseIdAndIsWaitListedIsTrue(user.getId(),purchase.getId()))
            throw new ApplicationErrorException(Constant.BOOKING_EXISTED);
        var savebooking = bookingRepository.save(Booking.builder()
                .bookingTime(DateUtils.getNowDate())
                .isWaitListed(true)
                .isCheckedIn(false)
                .depositCredits(0)
                .build());
        user.add(savebooking);
        purchase.add(savebooking);
        return savebooking;
    }

    private Booking saveBooking(BookingRequest bookingRequest) {
        var user = userRepository.findById(bookingRequest.getUserDTO().getId()).orElseThrow();
        var purchase = purchaseRepository.findById(bookingRequest.getPurchaseDTO().getId()).orElseThrow();
        if(bookingRepository.existsByUserIdAndPurchaseId(user.getId(),purchase.getId()))
            throw new ApplicationErrorException(Constant.BOOKING_EXISTED);
        boolean flat = AddPaymentCard(bookingRequest.getDepositCredits());
        Booking savebooking;
        if(flat){
            savebooking = bookingRepository.save(Booking.builder()
                    .bookingTime(DateUtils.getNowDate())
                    .isWaitListed(false)
                    .isCheckedIn(false)
                    .depositCredits(1)
                    .build());
            user.add(savebooking);
            purchase.add(savebooking);
        }else
            throw new ApplicationErrorException("Payment fail.");
        return savebooking;
    }

    @Cacheable(value = "bookings", key = "'all'")
    public CodigoResponse getAll() {
        var bookings = bookingRepository.findAll();
        return CodigoResponse.success(Constant.GET_ALL,entityListToBookingDTOList(bookings));
    }

    public boolean AddPaymentCard( int credit ){
        return credit == 1;
    }
}
