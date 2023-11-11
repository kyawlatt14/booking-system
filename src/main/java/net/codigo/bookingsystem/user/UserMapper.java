package net.codigo.bookingsystem.user;

import net.codigo.bookingsystem.base.entity.Booking;
import net.codigo.bookingsystem.base.entity.User;
import net.codigo.bookingsystem.booking.BookingDTO;

import java.util.ArrayList;
import java.util.List;

import static net.codigo.bookingsystem.booking.BookingMapper.entityListToBookingDTOList;

public class UserMapper {
    public static UserDTO entityToUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password("**********")
                .bookingDTOList(new ArrayList<>())
                .role(user.getRole())
                .country(user.getCountry())
                .createAt(user.getCreateAt())
                .build();
    }

    public static UserDTO entityToUserDTOWith(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password("**********")
                .bookingDTOList(entityListToBookingDTOList(user.getBookings()))
                .role(user.getRole())
                .country(user.getCountry())
                .createAt(user.getCreateAt())
                .build();
    }

    public static List<UserDTO> entityListToUserDTOList(List<User> users){
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> {
            userDTOS.add(entityToUserDTO(user));
        });
        return userDTOS;
    }


}
