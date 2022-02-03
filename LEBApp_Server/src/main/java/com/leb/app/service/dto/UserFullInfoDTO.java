package com.leb.app.service.dto;

import java.io.Serializable;

import com.leb.app.domain.User;
import com.leb.app.domain.UserInfo;

public class UserFullInfoDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private String phoneNumber;

    private String nib;

    private Integer nif;

    private String birthday;

    private String address;

    private String linkSocial;

    private Integer numberRequests;

    private Integer numberOfDeliveries;

    private Double numberOfKm;

    private Double payedValue;

    private Double availableBalance;

    private Double frozenBalance;

    private Double ranking;

    public UserFullInfoDTO(){
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNib() {
        return nib;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
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

    public String getLinkSocial() {
        return linkSocial;
    }

    public void setLinkSocial(String linkSocial) {
        this.linkSocial = linkSocial;
    }

    public Integer getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(Integer numberRequests) {
        this.numberRequests = numberRequests;
    }

    public Integer getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public void setNumberOfDeliveries(Integer numberOfDeliveries) {
        this.numberOfDeliveries = numberOfDeliveries;
    }

    public Double getNumberOfKm() {
        return numberOfKm;
    }

    public void setNumberOfKm(Double numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public Double getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(Double payedValue) {
        this.payedValue = payedValue;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Double frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public void update(User user, UserInfo userInfo){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.imageUrl = user.getEmail();
        this.phoneNumber = userInfo.getPhoneNumber();
        this.nib = userInfo.getNib();
        this.nif = userInfo.getNif();
        this.birthday = userInfo.getBirthday().toString();
        this.address = userInfo.getAddress();
        this.linkSocial = userInfo.getLinkSocial();
        this.numberRequests = userInfo.getNumberRequests();
        this.numberOfDeliveries = userInfo.getNumberOfDeliveries();
        this.numberOfKm = userInfo.getNumberOfKm();
        this.payedValue = userInfo.getPayedValue();
        this.availableBalance = userInfo.getAvailableBalance();
        this.frozenBalance = userInfo.getFrozenBalance();
        this.ranking = userInfo.getRanking();
    }

    @Override
    public String toString() {
        return "UserFullInfoDTO [address=" + address + ", availableBalance=" + availableBalance + ", birthday="
                + birthday + ", email=" + email + ", firstName=" + firstName + ", frozenBalance=" + frozenBalance
                + ", id=" + id + ", imageUrl=" + imageUrl + ", lastName=" + lastName + ", linkSocial=" + linkSocial
                + ", nib=" + nib + ", nif=" + nif + ", numberOfDeliveries=" + numberOfDeliveries + ", numberOfKm="
                + numberOfKm + ", numberRequests=" + numberRequests + ", payedValue=" + payedValue + ", phoneNumber="
                + phoneNumber + ", ranking=" + ranking + "]";
    }

}
