package com.example.accountbanking;

import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.AccountTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class AccountbankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountbankingApplication.class, args);
    }

}
