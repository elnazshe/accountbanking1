package com.example.accountbanking.dto;

import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.Address;
import com.example.accountbanking.entity.Tell;


import java.util.List;

public abstract class CostumerDto {

    private Long id;
    private String name;
    private String lastName;
    private List<Address> address;
    private  List<Account> account;
    private List<Tell> tell;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public List<Tell> getTell() {
        return tell;
    }

    public void setTell(List<Tell> tell) {
        this.tell = tell;
    }
}
