package com.example.shoppingcart.Model;

public class Company {


    private String companyName,city,email,phone,type,fax;

    public Company() {
    }

    public Company(String companyName, String city, String email, String phone, String type, String fax) {
        this.companyName = companyName;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.fax = fax;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
