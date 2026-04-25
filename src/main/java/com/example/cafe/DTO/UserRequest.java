package com.example.cafe.DTO;

import com.example.cafe.Entity.Tier;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Size(min = 3, max = 50, message = "names between 3 - 50 character pls")
    private String fullName;
    @Size(min = 3, max = 50, message = "email between 3 - 50 character pls")
    private String email;
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid phone number format :(")
    private String phone;
    @NotNull(message = "Error")
    private String password;
    @Size(max = 50, message = "pls keep it under 50 characters")
    private String defaultAddress;
    private Tier tier;
    private String avatar;
}
