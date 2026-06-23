package com.sunil.stockportfolio.controller;

import com.sunil.stockportfolio.entity.Stocks;
import com.sunil.stockportfolio.requestdto.StockRequest;
import com.sunil.stockportfolio.responsedto.StockResponse;
import com.sunil.stockportfolio.service.StocksService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StocksController {
    private final StocksService stocksService;
    private final RestResponseBuilder restResponseBuilder;

    @PostMapping("/stocks")
    public ResponseEntity<ResponseStructure<StockResponse>> createStock(@RequestBody StockRequest stockRequest, Stocks stock){
        StockResponse stockResponse = stocksService.createStock(stockRequest, stock);
        return restResponseBuilder.success(HttpStatus.CREATED,"Stock Created",stockResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<StockResponse>> getStocksById(@PathVariable Integer id){
        StockResponse stockResponse = stocksService.getStockById(id);
        return restResponseBuilder.success(HttpStatus.FOUND,"Stock Found", stockResponse);
    }
    @GetMapping
    public ResponseEntity<ResponseStructure<List<StockResponse>>> getAllStocks(){
        List<StockResponse> stockResponse = stocksService.getAllStocks();
        return restResponseBuilder.success(HttpStatus.FOUND,"Stock Found", stockResponse);
    }
    @GetMapping
    public ResponseEntity<ResponseStructure<StockResponse>> findStockBySymbol(@PathVariable String symbol){
        StockResponse stockResponse = stocksService.getStockBySymbol(symbol);
        return restResponseBuilder.success(HttpStatus.FOUND, "Stock Found", stockResponse);
    }

    public ResponseEntity<ResponseStructure<StockResponse>> disableStock(@PathVariable Integer id){
        StockResponse stockResponse = stocksService.disableStock(id);
        return restResponseBuilder.success(HttpStatus.LOCKED, "Stock disabled", stockResponse);
    }
}
