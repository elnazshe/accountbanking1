package com.example.accountbanking;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Costumer {
    @Id
    @GeneratedValue
    private String name;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    private  List<Account> accounts;
    @Version
    private Integer version;

    Costumer(){

    }
    public Costumer(String lastName, List<Address> addresses, List<Account> accounts, Integer version) {
        this.lastName = lastName;
        this.addresses = addresses;
        this.accounts = accounts;
        this.version = version;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
