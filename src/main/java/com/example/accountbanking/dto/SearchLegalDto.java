package com.example.accountbanking.dto;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;

public class SearchLegalDto {

    private String name;
    private String companyTpe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyTpe() {
        return companyTpe;
    }

    public void setCompanyTpe(String companyTpe) {
        this.companyTpe = companyTpe;
    }
}
