package com.leb.app.service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterDTO implements Serializable { 

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private String phoneNumber;

    private int nif;

    private String birthday;

    private String address;

    @JsonProperty("isTransporter")
    private boolean isTransporter;

    private String favouriteTransport;

    @JsonProperty("isProducer")
    private boolean isProducer;

    private String linkSocial;

    @JsonProperty("isPoint")
    private boolean isPoint;

    private String openingTimePoint;

    private String closingTimePoint;

    @JsonProperty("isDeliveryMan")
    private boolean isDeliveryMan;

    private String openingTime;

    private String closingTime;

    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isTransporter() {
        return isTransporter;
    }

    public void setTransporter(boolean isTransporter) {
        this.isTransporter = isTransporter;
    }

    public String getFavouriteTransport() {
        return favouriteTransport;
    }

    public void setFavouriteTransport(String favouriteTransport) {
        this.favouriteTransport = favouriteTransport;
    }

    public boolean isProducer() {
        return isProducer;
    }

    public void setProducer(boolean isProducer) {
        this.isProducer = isProducer;
    }

    public String getLinkSocial() {
        return linkSocial;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public void setPoint(boolean isPoint) {
        this.isPoint = isPoint;
    }

    public String getOpeningTimePoint() {
        return openingTimePoint;
    }

    public void setOpeningTimePoint(String openingTimePoint) {
        this.openingTimePoint = openingTimePoint;
    }

    public String getClosingTimePoint() {
        return closingTimePoint;
    }

    public void setClosingTimePoint(String closingTimePoint) {
        this.closingTimePoint = closingTimePoint;
    }

    public boolean isDeliveryMan() {
        return isDeliveryMan;
    }

    public void setDeliveryMan(boolean isDeliveryMan) {
        this.isDeliveryMan = isDeliveryMan;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterDTO() {
        super();
    }

    @Override
    public String toString() {
        return "RegisterDTO [address=" + address + ", birthday=" + birthday + ", closingTime=" + closingTime
                + ", closingTimePoint=" + closingTimePoint + ", email=" + email + ", favouriteTransport="
                + favouriteTransport + ", firstName=" + firstName + ", imageUrl=" + imageUrl + ", isDeliveryMan="
                + isDeliveryMan + ", isPoint=" + isPoint + ", isProducer=" + isProducer + ", isTransporter="
                + isTransporter + ", lastName=" + lastName + ", linkSocial=" + linkSocial + ", nif=" + nif
                + ", openingTime=" + openingTime + ", openingTimePoint=" + openingTimePoint + ", password=" + password
                + ", phoneNumber=" + phoneNumber + "]";
    }

}