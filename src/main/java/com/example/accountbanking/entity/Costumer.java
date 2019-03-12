package com.example.accountbanking.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public abstract class Costumer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address;
    @OneToMany (cascade = CascadeType.ALL )
    private  List<Account> account;
    @OneToMany (cascade = CascadeType.ALL )
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
