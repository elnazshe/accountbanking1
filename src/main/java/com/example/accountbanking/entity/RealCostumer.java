package com.example.accountbanking.entity;

import com.example.accountbanking.Gender;
import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.Costumer;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Inheritance
public class RealCostumer extends Costumer {

    @Id
    @GeneratedValue
    private Long id;
    private Gender gender;

    // @DateTimeFormat
    private String birthDate;
    private String nationalCode;
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}


