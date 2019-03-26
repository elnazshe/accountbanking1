package com.example.accountbanking.facade;

import com.example.accountbanking.dto.*;
import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.LegalCostumer;
import com.example.accountbanking.entity.RealCostumer;

import java.util.List;


public interface CustomerFacade {


    String deposit(MoneyTransferDto moneyTransferDto);
    String take( MoneyTransferDto moneyTransferDto);
    String transfer(MoneyTransferDto moneyTransferDto);
    String saveLegal(LegalCostumerDto legalCostumerDto);
    String save(RealCostumerDto realCostumerDto);
    LegalCostumer findLegalByCompanyNumber(String companyNumber);
    List<LegalCostumer> findLegal(SearchLegalDto searchLegalDto);
    List<RealCostumer> findRealByNationalCode(String nationalCode);
    List<RealCostumer> findReal(SearchRealDto searchRealDto);
    List<Account> getAccountByNumber( String accountNumber);
    String deleteReal(RealCostumerDto realCostumerDto);
    String deleteLegal(LegalCostumerDto legalCostumerDto);
    void calculateDaily();
    void calculateIntrest();


}



