package com.example.accountbanking.entity;

import com.example.accountbanking.dto.Gender;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Table(name = "ELN_REALCOSTUMER")
@Entity()
@EntityListeners(AuditingEntityListener.class)
@Inheritance
public class RealCostumer extends Costumer {

    @Id
    @GeneratedValue
    private Long id;
    private Gender gender;

    @DateTimeFormat
    private Date birthDate;
    private String nationalCode;
    @Version
    private Integer version;
    private Integer isDeleted;


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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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


