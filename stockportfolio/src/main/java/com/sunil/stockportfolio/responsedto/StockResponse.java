package com.sunil.stockportfolio.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private Integer id;
    private String symbol;
    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal previousPrice;
    private BigInteger volume;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
