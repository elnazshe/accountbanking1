package com.example.accountbanking;

import java.util.List;

public class RealCostumer extends Costumer {
    private Gender gender;
    private String birthDate;
    RealCostumer(){

    }

    public RealCostumer(Gender gender, String birthDate) {
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public RealCostumer(String lastName, List<Address> addresses, List<Account> accounts, Integer version, Gender gender, String birthDate) {
        super(lastName, addresses, accounts, version);
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
