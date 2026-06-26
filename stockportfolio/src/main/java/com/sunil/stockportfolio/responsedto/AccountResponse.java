package com.sunil.stockportfolio.responsedto;

import com.sunil.stockportfolio.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Integer id;
    private User user;
    private Integer accountNumber;
    private BigDecimal balance;
    private boolean active;
    private LocalDateTime createdAt;
}
