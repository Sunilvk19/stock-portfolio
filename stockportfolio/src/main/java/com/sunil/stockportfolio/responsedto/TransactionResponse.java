package com.sunil.stockportfolio.responsedto;

import com.sunil.stockportfolio.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Integer id;
    private Integer portfolioId;
    private String stockSymbol;
    private String companyName;
    private BigDecimal currentPrice;
    private TransactionType transactionType;
    private Integer quantity;
    private LocalDateTime createdAt;
}
