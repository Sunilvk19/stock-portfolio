package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.entity.Holding;
import com.sunil.stockportfolio.requestdto.HoldingRequest;
import com.sunil.stockportfolio.responsedto.HoldingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HoldingService {

    List<HoldingResponse> getHoldingByPortfolio(Integer portfolioId);

    List<HoldingResponse> getHoldingByPortfolioAndStocks(Integer portfolioId, Integer stockId);

    HoldingResponse saveHolding(HoldingRequest holdingRequest);
}
