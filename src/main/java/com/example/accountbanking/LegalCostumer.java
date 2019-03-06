package com.example.accountbanking;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

public class LegalCostumer extends Costumer {
    private String companyNumber;
    private String companyTpe;

    LegalCostumer(){

    }

    public LegalCostumer(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public LegalCostumer(String lastName, List<Address> addresses, List<Account> accounts, Integer version, String companyNumber) {
        super(lastName, addresses, accounts, version);
        this.companyNumber = companyNumber;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyTpe() {
        return companyTpe;
    }

    public void setCompanyTpe(String companyTpe) {
        this.companyTpe = companyTpe;
    }
}
