package com.example.accountbanking.facade;

import com.example.accountbanking.controler.ServiceController;
import com.example.accountbanking.dto.*;
import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.LegalCostumer;
import com.example.accountbanking.entity.RealCostumer;
import com.example.accountbanking.facade.CustomerFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;


public  class CustomerFacadeImpl implements CustomerFacade {

    ServiceController serviceController;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String deposit(MoneyTransferDto moneyTransferDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String take(MoneyTransferDto moneyTransferDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String transfer(MoneyTransferDto moneyTransferDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String saveLegal(LegalCostumerDto legalCostumerDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String save(RealCostumerDto realCostumerDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public LegalCostumer findLegalByCompanyNumber(String companyNumber) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<LegalCostumer> findLegal(SearchLegalDto searchLegalDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<RealCostumer> findRealByNationalCode(String nationalCode) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<RealCostumer> findReal(SearchRealDto searchRealDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<Account> getAccountByNumber(String accountNumber) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String deleteReal(RealCostumerDto realCostumerDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String deleteLegal(LegalCostumerDto legalCostumerDto) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void calculateDaily() {

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void calculateIntrest() {

    }

}
