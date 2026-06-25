package com.sunil.stockportfolio.controller;

import com.sunil.stockportfolio.entity.Holding;
import com.sunil.stockportfolio.requestdto.HoldingRequest;
import com.sunil.stockportfolio.responsedto.HoldingResponse;
import com.sunil.stockportfolio.service.HoldingService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class HoldingController {
    private final HoldingService holdingService;
    private final RestResponseBuilder restResponseBuilder;

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<ResponseStructure<List<HoldingResponse>>> getHoldingsByPortfolio(@PathVariable Integer portfolioId){
        List<HoldingResponse> holdingResponse = holdingService.getHoldingByPortfolio(portfolioId);
        return restResponseBuilder.success(HttpStatus.FOUND,"Found Portfolio", holdingResponse);
    }
    @GetMapping("/portfolio/{portfolioId}/stock/{stockId}")
    public ResponseEntity<ResponseStructure<List<HoldingResponse>>> getHoldingsByPortfolioAndStocks(@PathVariable Integer portfolioId, @PathVariable Integer stockId){
        List<HoldingResponse> holdingResponseList = holdingService.getHoldingByPortfolioAndStocks(portfolioId, stockId);
        return restResponseBuilder.success(HttpStatus.FOUND, "Found Portfolio And Stocks", holdingResponseList);
    }

    public ResponseEntity<ResponseStructure<HoldingResponse>> saveHolding(@RequestBody HoldingRequest holdingRequest){
        HoldingResponse holdingResponse = holdingService.saveHolding(holdingRequest);
        return  restResponseBuilder.success(HttpStatus.CREATED, "Holding Is Saved", holdingResponse);
    }
}
