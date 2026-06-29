package com.sunil.stockportfolio.service;


import com.sunil.stockportfolio.requestdto.BuyStockRequest;
import com.sunil.stockportfolio.requestdto.SellStockRequest;
import com.sunil.stockportfolio.requestdto.TransactionRequest;
import com.sunil.stockportfolio.responsedto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    TransactionResponse buyStock(BuyStockRequest buyStockRequest);

    TransactionResponse sellStock(SellStockRequest sellStockRequest);

    List<TransactionResponse> transactionHistory(TransactionRequest request);
}
