package com.sunil.stockportfolio.requestdto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AddFoundsRequest {
    private BigDecimal amount;
}
