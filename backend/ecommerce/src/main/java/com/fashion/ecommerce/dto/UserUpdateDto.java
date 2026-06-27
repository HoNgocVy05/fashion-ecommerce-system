package com.fashion.ecommerce.dto;

public class UserUpdateDto {

    private String email;

    private String fullname;

    private String gender;

    private String phoneNumber;

    private String password;

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getFullname(){return fullname;}

    public void setFullname(String fullname){this.fullname = fullname;}

    public String getGender(){return gender;}

    public void setGender(String gender){this.gender = gender;}

    public String getPhoneNumber(){return phoneNumber;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}
}