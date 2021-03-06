package com.example.accountbanking.dto;

import com.example.accountbanking.entity.AccountTransaction;


import java.math.BigDecimal;
import java.util.List;

public class AccountDto {

    private Long id;
    private String accountNumber;
    private BigDecimal amount;
    private AccountType accountType;
    private Double rate;
    private BigDecimal benefit;
    private List<AccountTransaction> accountTransactionList;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public BigDecimal getBenefit() {
        return benefit;
    }

    public void setBenefit(BigDecimal benefit) {
        this.benefit = benefit;
    }

    public List<AccountTransaction> getAccountTransactionList() {
        return accountTransactionList;
    }

    public void setAccountTransactionList(List<AccountTransaction> accountTransactionList) {
        this.accountTransactionList = accountTransactionList;
    }
}
