package com.sunil.stockportfolio.controller;

import com.sunil.stockportfolio.requestdto.BuyStockRequest;
import com.sunil.stockportfolio.requestdto.SellStockRequest;
import com.sunil.stockportfolio.requestdto.TransactionRequest;
import com.sunil.stockportfolio.responsedto.TransactionResponse;
import com.sunil.stockportfolio.service.TransactionService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final RestResponseBuilder restResponseBuilder;
    @PostMapping("/buy")
    public ResponseEntity<ResponseStructure<TransactionResponse>> buyStock(@RequestBody BuyStockRequest buyStockRequest){
        TransactionResponse transactionResponse = transactionService.buyStock(buyStockRequest);
        return restResponseBuilder.success(HttpStatus.CREATED,"Successfully buy stock", transactionResponse);
    }
    @PostMapping("/sell")
    public ResponseEntity<ResponseStructure<TransactionResponse>> sellStock(@RequestBody SellStockRequest sellStockRequest){
        TransactionResponse transactionResponse = transactionService.sellStock(sellStockRequest);
        return restResponseBuilder.success(HttpStatus.CREATED, "Successfully sell stock", transactionResponse);
    }
    @GetMapping("/history")
    public ResponseEntity<ResponseStructure<List<TransactionResponse>>> transactionHistory(@RequestBody TransactionRequest request){
        List<TransactionResponse> responseList = transactionService.transactionHistory(request);
        return restResponseBuilder.success(HttpStatus.OK,"Transaction History",responseList);
    }
}
