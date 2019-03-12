package com.example.accountbanking.entity;


import com.example.accountbanking.dto.PhoneType;

import javax.persistence.*;

@Entity
public class Tell {

    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private PhoneType phoneType;
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
