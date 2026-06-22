package com.sunil.stockportfolio.responsedto;


import com.sunil.stockportfolio.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private Role role;
}
