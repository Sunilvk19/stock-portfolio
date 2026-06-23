package com.sunil.stockportfolio.serviceImpl;

import com.sunil.stockportfolio.entity.Portfolio;
import com.sunil.stockportfolio.entity.User;
import com.sunil.stockportfolio.exceptionhandle.PortfolioNotFoundException;
import com.sunil.stockportfolio.repository.AuthUserRepository;
import com.sunil.stockportfolio.repository.PortfolioRepository;
import com.sunil.stockportfolio.responsedto.PortfolioResponse;
import com.sunil.stockportfolio.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AuthUserRepository authUserRepository;

    private PortfolioResponse getPortfolioResponse(Portfolio portfolio, User user) {
        Portfolio savedPortfolio =
                portfolioRepository.save(portfolio);
        return PortfolioResponse.builder()
                .id(savedPortfolio.getId())
                .totalInvestment(savedPortfolio.getTotalInvestment())
                .currentValue(savedPortfolio.getCurrentValue())
                .profitLoss(savedPortfolio.getProfitLoss())
                .userId(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public PortfolioResponse createPortfolio(Integer userId) {
        User user = authUserRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if (user.getPortfolio() != null){
            throw new RuntimeException("Portfolio already exists for this user");
        }
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        user.setPortfolio(portfolio);
        portfolio.setTotalInvestment(BigDecimal.ZERO);
        portfolio.setCurrentValue(BigDecimal.ZERO);
        portfolio.setProfitLoss(BigDecimal.ZERO);
        return getPortfolioResponse(portfolio, user);
    }

    @Override
    public PortfolioResponse getPortfolio(Integer userId) {
        User user = authUserRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        Portfolio portfolio = user.getPortfolio();
        if(portfolio == null){
            throw new PortfolioNotFoundException("Portfolio not found");
        }
        return getPortfolioResponse(portfolio, user);
    }

}
