package com.leb.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.Producer} entity.
 */
public class ProducerDTO implements Serializable {

    private Long id;

    private String name;

    private String mail;

    private String phoneNumber;

    private String nib;

    private Integer nif;

    private LocalDate birthday;

    private String adress;

    private String photo;

    private String linkSocial;

    private Integer numberRequests;

    private Double payedValue;

    private Double valueToPay;

    private Double ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Double getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(Double payedValue) {
        this.payedValue = payedValue;
    }

    public Double getValueToPay() {
        return valueToPay;
    }

    public void setValueToPay(Double valueToPay) {
        this.valueToPay = valueToPay;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProducerDTO)) {
            return false;
        }

        ProducerDTO producerDTO = (ProducerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, producerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProducerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nib='" + getNib() + "'" +
            ", nif=" + getNif() +
            ", birthday='" + getBirthday() + "'" +
            ", adress='" + getAdress() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", linkSocial='" + getLinkSocial() + "'" +
            ", numberRequests=" + getNumberRequests() +
            ", payedValue=" + getPayedValue() +
            ", valueToPay=" + getValueToPay() +
            ", ranking=" + getRanking() +
            "}";
    }
}
