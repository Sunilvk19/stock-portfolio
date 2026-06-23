package com.sunil.stockportfolio.requestdto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class StockRequest {
    private String symbol;
    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal previousPrice;
}
