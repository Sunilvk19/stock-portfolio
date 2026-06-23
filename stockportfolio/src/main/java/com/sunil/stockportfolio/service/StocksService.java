package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.entity.Stocks;
import com.sunil.stockportfolio.requestdto.StockRequest;
import com.sunil.stockportfolio.responsedto.StockResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StocksService {

    StockResponse createStock(StockRequest stockRequest, Stocks stock);


    StockResponse getStockById(Integer id);


    List<StockResponse> getAllStocks();

    StockResponse getStockBySymbol(String symbol);

    StockResponse disableStock(Integer id);
}
