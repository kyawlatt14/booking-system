package net.codigo.bookingsystem.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.codigo.bookingsystem.base.entity.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Long id;
    private String username;
    private String address;
    private String email;
    private String phone;
    private String password;
    private String newPassword;
    private Role role;
    private String country;
}
