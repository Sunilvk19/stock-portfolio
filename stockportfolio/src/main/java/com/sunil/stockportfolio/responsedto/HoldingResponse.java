package com.sunil.stockportfolio.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoldingResponse {
    private Integer id;
    private Integer stockId;
    private Integer portfolioId;
    private Integer quantity;
    private String stockSymbol;
    private String companyName;
    private BigDecimal averagePrice;
}
