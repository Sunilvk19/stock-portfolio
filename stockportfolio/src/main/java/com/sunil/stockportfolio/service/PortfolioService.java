package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.responsedto.PortfolioResponse;
import org.springframework.stereotype.Service;

@Service
public interface PortfolioService {
    PortfolioResponse createPortfolio(Integer userId);

    PortfolioResponse getPortfolio(Integer userId);
}
