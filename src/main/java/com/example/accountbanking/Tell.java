package com.example.accountbanking;

public class Tell {
    private String number;
    private TypePhone typePhone;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TypePhone getTypePhone() {
        return typePhone;
    }

    public void setTypePhone(TypePhone typePhone) {
        this.typePhone = typePhone;
    }
    Tell(){

    }

    public Tell(String number, TypePhone typePhone) {
        this.number = number;
        this.typePhone = typePhone;
    }
}
