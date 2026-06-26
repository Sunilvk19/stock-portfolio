package com.sunil.stockportfolio.serviceImpl;

import com.sunil.stockportfolio.entity.Stocks;
import com.sunil.stockportfolio.exceptionhandle.StocksNotFoundException;
import com.sunil.stockportfolio.repository.StockRepository;
import com.sunil.stockportfolio.requestdto.StockRequest;
import com.sunil.stockportfolio.responsedto.StockResponse;
import com.sunil.stockportfolio.service.StocksService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StocksService {
    private final StockRepository stockRepository;

    private Stocks mapToStocks(StockRequest stockRequest, Stocks stock) {
        stock.setSymbol(stockRequest.getSymbol());
        stock.setCompanyName(stockRequest.getCompanyName());
        stock.setCurrentPrice(stockRequest.getCurrentPrice());
        stock.setPreviousPrice(stockRequest.getPreviousPrice());
        return stock;
    }

    private static  StockResponse mapToStockResponse(Stocks stock) {
        return StockResponse.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .currentPrice(stock.getCurrentPrice())
                .previousPrice(stock.getPreviousPrice())
                .isActive(stock.isActive())
                .build();
    }
    @Override
    public StockResponse createStock(StockRequest stockRequest, Stocks stock) {
        stock = mapToStocks(stockRequest, stock);
        stock.setActive(true);
        Stocks saveStock = stockRepository.save(stock);
        return mapToStockResponse(saveStock);
    }

    @Override
    public StockResponse getStockById(Integer id) {
        Stocks stock = stockRepository.findById(id)
                .orElseThrow(()-> new StocksNotFoundException("Stock Not Found"));
        return mapToStockResponse(stock);
    }

    @Override
    public List<StockResponse> getAllStocks() {
        return stockRepository.findAll()
                .stream()
                .map(StockServiceImpl::mapToStockResponse)
                .toList();
    }

    @Override
    public StockResponse getStockBySymbol(String symbol) {
        Optional<Stocks> stocks = stockRepository.findBySymbol(symbol);
        if (stocks.isEmpty()){
            throw new StocksNotFoundException("Stock Not Found");
        }
        return mapToStockResponse(stocks.get());
    }

    @Override
    public StockResponse disableStock(Integer id) {
        Stocks stock = stockRepository.findById(id)
                .orElseThrow(()-> new StocksNotFoundException("Stock Not Found"));
        stock.setActive(false);
        Stocks updateStock = stockRepository.save(stock);
        return mapToStockResponse(updateStock);
    }

}
