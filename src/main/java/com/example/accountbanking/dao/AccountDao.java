package com.example.accountbanking.dao;


import com.example.accountbanking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountDao extends JpaRepository<Account, Integer> {

    @Query("select a from Account a ")
    List<Account> getAllAccount ();


    @Query ("select a from Account a where a.accountNumber =:accountNumber")
    Account getAccountByNumber (@Param("accountNumber") String accountNumber);
}
