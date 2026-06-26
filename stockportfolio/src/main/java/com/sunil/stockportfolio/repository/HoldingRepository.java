package com.sunil.stockportfolio.repository;

import com.sunil.stockportfolio.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Integer> {
    List<Holding> findByPortfolioId(Integer portfolioId);

    Optional<Holding> findByPortfolioIdAndStockId(Integer portfolioId, Integer stockId);
}
