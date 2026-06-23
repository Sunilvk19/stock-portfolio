package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.entity.Portfolio;
import com.sunil.stockportfolio.responsedto.PortfolioResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PortfolioService {
    PortfolioResponse createPortfolio(Integer userId);

    PortfolioResponse getPortfolio(Integer userId);
}
