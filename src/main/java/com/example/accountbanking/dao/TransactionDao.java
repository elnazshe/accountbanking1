package com.example.accountbanking.dao;

import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<AccountTransaction, Integer> {
}
