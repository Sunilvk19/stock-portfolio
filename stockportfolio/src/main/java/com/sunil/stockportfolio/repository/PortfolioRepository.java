package com.sunil.stockportfolio.repository;

import com.sunil.stockportfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
}
