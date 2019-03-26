package com.example.accountbanking.dto;



import java.util.Date;


public class LegalCostumerDto extends CostumerDto {

    private Long id;
    private String companyNumber;
    private String companyTpe;
    private Date registrationDate;
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

    public Integer getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Integer deleted) {
        isDeleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

