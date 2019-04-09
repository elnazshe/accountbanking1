package com.example.accountbanking.entity;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Table(name = "ELN_LEGALCOSTUMER")
@Entity()
@EntityListeners(AuditingEntityListener.class)
@Inheritance
public class LegalCostumer extends Costumer {

    @Id
    @GeneratedValue
    private Long id;
    private String companyNumber;
    private String companyTpe;
    @DateTimeFormat
    private Date registrationDate;
    @Version
    private Integer version;
    private Integer isDeleted;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }



    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
