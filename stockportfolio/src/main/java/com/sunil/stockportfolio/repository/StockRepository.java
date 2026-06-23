package com.sunil.stockportfolio.repository;

import com.sunil.stockportfolio.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stocks, Integer> {
    Optional<Stocks> findBySymbol(String symbol);
}
