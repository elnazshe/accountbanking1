package com.example.accountbanking.entity;


import com.example.accountbanking.dto.AccountTransferType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity

public class AccountTransaction {
    @Id
    @GeneratedValue
    private Long id;
    private AccountTransferType accountTransferType;
    private BigDecimal amount;
    private Date transactionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountTransferType getAccountTransferType() {
        return accountTransferType;
    }

    public void setAccountTransferType(AccountTransferType accountTransferType) {
        this.accountTransferType = accountTransferType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
