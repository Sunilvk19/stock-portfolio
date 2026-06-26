package com.sunil.stockportfolio.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuyStockRequest {
    private Integer portfolioId;
    private Integer stockId;
    private Integer quantity;
}
