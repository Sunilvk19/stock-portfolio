package com.sunil.stockportfolio.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoldingRequest {
    private Integer stockId;
    private Integer portfolioId;
    private Integer quantity;
}
