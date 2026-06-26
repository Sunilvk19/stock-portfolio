package com.sunil.stockportfolio.requestdto;

import com.sunil.stockportfolio.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private Integer portfolioId;
    private Integer stockId;
    private Integer quantity;
    private TransactionType transactionType;
}
