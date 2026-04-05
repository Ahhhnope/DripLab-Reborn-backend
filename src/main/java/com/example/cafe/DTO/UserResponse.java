package com.example.cafe.DTO;

import com.example.cafe.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String defaultAddress;
    private String avatar;
    private String role;
    private String token;

    public UserResponse(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.defaultAddress = user.getDefaultAddress();
        this.avatar = user.getAvatar();
        this.role = user.getRole();
    }
}
