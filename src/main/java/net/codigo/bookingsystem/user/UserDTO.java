package net.codigo.bookingsystem.user;

import lombok.Builder;
import lombok.Data;
import net.codigo.bookingsystem.base.entity.enums.Role;
import net.codigo.bookingsystem.booking.BookingDTO;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String address;
    private long createAt;
    private String country;
    private Role role;
    private List<BookingDTO> bookingDTOList;
}
