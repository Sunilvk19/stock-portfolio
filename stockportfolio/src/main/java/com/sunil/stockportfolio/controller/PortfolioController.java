package com.sunil.stockportfolio.controller;

import com.sunil.stockportfolio.responsedto.PortfolioResponse;
import com.sunil.stockportfolio.service.PortfolioService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseStructure<PortfolioResponse>> createPortfolio(@PathVariable Integer userId){
        PortfolioResponse savePortfolio = portfolioService.createPortfolio(userId);
        return responseBuilder.success(HttpStatus.CREATED,"Created Portfolio", savePortfolio);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<PortfolioResponse>> getPortfolio(@PathVariable Integer userId){
        PortfolioResponse savePortfolio = portfolioService.getPortfolio(userId);
        return responseBuilder.success(HttpStatus.OK,"Portfolio fetch Successfully", savePortfolio);
    }
}
