package com.sunil.stockportfolio.serviceImpl;
import com.sunil.stockportfolio.entity.Holding;
import com.sunil.stockportfolio.entity.Portfolio;
import com.sunil.stockportfolio.entity.Stocks;
import com.sunil.stockportfolio.entity.Transaction;
import com.sunil.stockportfolio.enums.TransactionType;
import com.sunil.stockportfolio.exceptionhandle.HoldingNotFoundException;
import com.sunil.stockportfolio.exceptionhandle.PortfolioNotFoundException;
import com.sunil.stockportfolio.exceptionhandle.StocksNotFoundException;
import com.sunil.stockportfolio.repository.HoldingRepository;
import com.sunil.stockportfolio.repository.PortfolioRepository;
import com.sunil.stockportfolio.repository.StockRepository;
import com.sunil.stockportfolio.repository.TransactionRepository;
import com.sunil.stockportfolio.requestdto.BuyStockRequest;
import com.sunil.stockportfolio.requestdto.SellStockRequest;
import com.sunil.stockportfolio.requestdto.TransactionRequest;
import com.sunil.stockportfolio.responsedto.TransactionResponse;
import com.sunil.stockportfolio.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private static TransactionResponse mapToTransaction(Transaction savedTransaction, Portfolio portfolio, Stocks stock) {
        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .portfolioId(portfolio.getId())
                .stockSymbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .currentPrice(savedTransaction.getCurrentPrice())
                .transactionType(savedTransaction.getTransactionType())
                .quantity(savedTransaction.getQuantity())
                .createdAt(savedTransaction.getCreatedAt())
                .build();
    }
    @Override
    @Transactional
    public TransactionResponse buyStock(BuyStockRequest buyStockRequest) {
        if (buyStockRequest.getQuantity() == null || buyStockRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        Portfolio portfolio = portfolioRepository.findById(buyStockRequest.getPortfolioId())
                .orElseThrow(()-> new PortfolioNotFoundException("Portfolio Not Found"));
        Stocks stock = stockRepository.findById(buyStockRequest.getStockId())
                .orElseThrow(()-> new StocksNotFoundException("Stock Not Found"));
        if (!stock.isActive()){
            throw new StocksNotFoundException("Stock is Not Active");
        }
        Optional<Holding> optional = holdingRepository.findByPortfolioIdAndStockId(buyStockRequest.getPortfolioId(),
                buyStockRequest.getStockId());
        Holding holding;
        BigDecimal buyPrice = stock.getCurrentPrice();
        Integer buyQuantity = buyStockRequest.getQuantity();
        if (optional.isPresent()){
            holding = optional.get();
            Integer oldQuantity = holding.getQuantity();
            Integer newQuantity = oldQuantity + buyQuantity;
            BigDecimal oldInvestment = holding.getAveragePrice().multiply(BigDecimal.valueOf(oldQuantity));
            BigDecimal newInvestment = buyPrice.multiply(BigDecimal.valueOf(buyQuantity));
            BigDecimal averagePrice = oldInvestment.add(newInvestment)
                    .divide(BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);
            holding.setQuantity(newQuantity);
            holding.setAveragePrice(averagePrice);
        } else {
            holding = Holding.builder()
                    .portfolio(portfolio)
                    .stock(stock)
                    .quantity(buyQuantity)
                    .averagePrice(buyPrice)
                    .build();
        }
        holdingRepository.save(holding);
        BigDecimal transactionValue = buyPrice.multiply(BigDecimal.valueOf(buyQuantity));
        portfolio.setTotalInvestment(portfolio.getTotalInvestment().add(transactionValue));
        portfolio.setCurrentValue(portfolio.getCurrentValue().add(transactionValue));
        portfolio.setProfitLoss(portfolio.getCurrentValue().subtract(portfolio.getTotalInvestment()));
        portfolioRepository.save(portfolio);
        Transaction transaction = Transaction.builder()
                .portfolio(portfolio)
                .stock(stock)
                .quantity(buyQuantity)
                .currentPrice(buyPrice)
                .transactionType(TransactionType.BUY)
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToTransaction(savedTransaction, portfolio, stock);
    }
    @Override
    @Transactional
    public TransactionResponse sellStock(SellStockRequest sellStockRequest) {
        if(sellStockRequest.getQuantity() == null || sellStockRequest.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        Portfolio portfolio = portfolioRepository.findById(sellStockRequest.getPortfolioId())
                .orElseThrow(()-> new PortfolioNotFoundException("Portfolio Not Found"));
        Stocks stock = stockRepository.findById(sellStockRequest.getStockId())
                .orElseThrow(()-> new StocksNotFoundException("Stock Not Found"));
        if(!stock.isActive()){
            throw new StocksNotFoundException("Stock is Not Active");
        }
        Optional<Holding> optional = holdingRepository.findByPortfolioIdAndStockId(sellStockRequest.getPortfolioId(),
                sellStockRequest.getStockId());
        if (optional.isEmpty()){
            throw new HoldingNotFoundException("Stock not found in portfolio");
        }
        Holding holding = optional.get();
        Integer oldQuantity = holding.getQuantity();
        Integer sellQuantity = sellStockRequest.getQuantity();
        if (sellQuantity > oldQuantity) {
            throw new IllegalArgumentException("Insufficient stock quantity");
        }
        BigDecimal sellPrice = stock.getCurrentPrice();
        BigDecimal soldCostBasis = holding.getAveragePrice().multiply(BigDecimal.valueOf(sellQuantity));
        BigDecimal realizedMarketValue = sellPrice.multiply(BigDecimal.valueOf(sellQuantity));
        Integer newQuantity = oldQuantity -  sellQuantity;
        if (newQuantity > 0){
            holding.setQuantity(newQuantity);
            holdingRepository.save(holding);
        } else {
            holdingRepository.delete(holding);
        }
        portfolio.setTotalInvestment(portfolio.getTotalInvestment().subtract(soldCostBasis));
        portfolio.setCurrentValue(portfolio.getCurrentValue().subtract(realizedMarketValue));
        portfolio.setProfitLoss(portfolio.getCurrentValue().subtract(portfolio.getTotalInvestment()));
        portfolioRepository.save(portfolio);
        Transaction transaction = Transaction.builder()
                .portfolio(portfolio)
                .stock(stock)
                .quantity(sellQuantity)
                .currentPrice(sellPrice)
                .transactionType(TransactionType.SELL)
                .build();
        Transaction savedTran = transactionRepository.save(transaction);
        return mapToTransaction(savedTran, portfolio, stock);
    }
    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> transactionHistory(TransactionRequest request) {
        if (request.getPortfolioId() == null) {
            throw new IllegalArgumentException("Portfolio id is required");
        }
        if (!portfolioRepository.existsById(request.getPortfolioId())) {
            throw new PortfolioNotFoundException("Portfolio Not Found");
        }
        return transactionRepository.findByPortfolioIdOrderByCreatedAtDesc(request.getPortfolioId()).stream()
                .filter(transaction -> request.getStockId() == null
                        || transaction.getStock().getId().equals(request.getStockId()))
                .filter(transaction -> request.getTransactionType() == null
                        || transaction.getTransactionType().equals(request.getTransactionType()))
                .filter(transaction -> request.getQuantity() == null
                        || transaction.getQuantity().equals(request.getQuantity()))
                .map(transaction -> mapToTransaction(transaction, transaction.getPortfolio(), transaction.getStock()))
                .toList();
    }
}
