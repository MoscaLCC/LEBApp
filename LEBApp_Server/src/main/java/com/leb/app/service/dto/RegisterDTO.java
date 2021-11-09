package com.leb.app.service.dto;

import java.io.Serializable;
import java.time.Instant;

//import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterDTO implements Serializable { 

    private String email;

    private String phoneNumber;

    private Integer nif;
    
    private String firstName;

    private String lastName;

    private String password;

    private String langKey;

    private String address;

    private String birthday;

    private String linkSocial;

    private String imageUrl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLinkSocial() {
        return linkSocial;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        result = prime * result + ((langKey == null) ? 0 : langKey.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((linkSocial == null) ? 0 : linkSocial.hashCode());
        result = prime * result + ((nif == null) ? 0 : nif.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RegisterDTO other = (RegisterDTO) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (imageUrl == null) {
            if (other.imageUrl != null)
                return false;
        } else if (!imageUrl.equals(other.imageUrl))
            return false;
        if (langKey == null) {
            if (other.langKey != null)
                return false;
        } else if (!langKey.equals(other.langKey))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (linkSocial == null) {
            if (other.linkSocial != null)
                return false;
        } else if (!linkSocial.equals(other.linkSocial))
            return false;
        if (nif == null) {
            if (other.nif != null)
                return false;
        } else if (!nif.equals(other.nif))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RegisterDTO [address=" + address + ", birthday=" + birthday + ", email=" + email + ", firstName="
                + firstName + ", imageUrl=" + imageUrl + ", langKey=" + langKey + ", lastName=" + lastName
                + ", linkSocial=" + linkSocial + ", nif=" + nif + ", password=" + password + ", phoneNumber="
                + phoneNumber + "]";
    }

    

    //@JsonProperty("isDeliveryMan")
    //private boolean isDeliveryMan;

    

}