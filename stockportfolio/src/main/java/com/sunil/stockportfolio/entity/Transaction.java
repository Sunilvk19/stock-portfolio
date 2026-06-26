package com.sunil.stockportfolio.entity;

import com.sunil.stockportfolio.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch =  FetchType.LAZY)
    private Portfolio portfolio;
    @ManyToOne(fetch = FetchType.LAZY)
    private Stocks stock;
    private Integer quantity;
    private BigDecimal currentPrice;
    private TransactionType transactionType;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
