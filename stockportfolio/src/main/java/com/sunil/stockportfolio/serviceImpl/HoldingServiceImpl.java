package com.sunil.stockportfolio.serviceImpl;


import com.sunil.stockportfolio.entity.Holding;
import com.sunil.stockportfolio.entity.Portfolio;
import com.sunil.stockportfolio.entity.Stocks;
import com.sunil.stockportfolio.exceptionhandle.PortfolioNotFoundException;
import com.sunil.stockportfolio.exceptionhandle.StocksNotFoundException;
import com.sunil.stockportfolio.repository.HoldingRepository;
import com.sunil.stockportfolio.repository.PortfolioRepository;
import com.sunil.stockportfolio.repository.StockRepository;
import com.sunil.stockportfolio.requestdto.HoldingRequest;
import com.sunil.stockportfolio.responsedto.HoldingResponse;
import com.sunil.stockportfolio.service.HoldingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class HoldingServiceImpl implements HoldingService {
    private final HoldingRepository holdingRepository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    private static HoldingResponse mapToHoldingResponse(Holding holding){
        return HoldingResponse.builder()
                .id(holding.getId())
                .stockId(holding.getStock().getId())
                .stockSymbol(holding.getStock().getSymbol())
                .portfolioId(holding.getPortfolio().getId())
                .companyName(holding.getStock().getCompanyName())
                .quantity(holding.getQuantity())
                .averagePrice(holding.getAveragePrice())
                .build();
    }
    @Override
    public List<HoldingResponse> getHoldingByPortfolio(Integer portfolioId) {
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        if(holdings.isEmpty()){
            return Collections.emptyList();
        }
        return holdings.stream()
                .map(HoldingServiceImpl::mapToHoldingResponse)
                .toList();
    }

    @Override
    public List<HoldingResponse> getHoldingByPortfolioAndStocks(Integer portfolioId, Integer stockId) {
        Optional<Holding> holdingsList = holdingRepository.findByPortfolioIdAndStockId(portfolioId,stockId);
        if(holdingsList.isEmpty()){
            return Collections.emptyList();
        }
        return holdingsList.stream()
                .map(HoldingServiceImpl::mapToHoldingResponse)
                .toList();
    }

    @Override
    public HoldingResponse saveHolding(HoldingRequest holdingRequest) {
        Portfolio portfolio = portfolioRepository.findById(holdingRequest.getPortfolioId())
                .orElseThrow(()-> new PortfolioNotFoundException("Portfolio Not Found"));
        Stocks stock = stockRepository.findById(holdingRequest.getStockId())
                .orElseThrow(()-> new StocksNotFoundException("Stock Not Found"));
        Holding holding = new Holding();
        holding.setPortfolio(portfolio);
        holding.setStock(stock);
        holding.setQuantity(holding.getQuantity());
        holding.setAveragePrice(holding.getAveragePrice());
        Holding savedHolding = holdingRepository.save(holding);
        return mapToHoldingResponse(savedHolding);
    }

}
