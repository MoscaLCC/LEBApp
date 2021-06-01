package com.leb.app.service.dto;

import com.leb.app.config.Constants;
import com.leb.app.domain.Authority;
import com.leb.app.domain.User;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO representing a user, with his authorities.
 */

public class AdminUserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private String createdDate;

    private String lastModifiedBy;

    private String lastModifiedDate;

    private String phoneNumber;

    private String nib;

    private int nif;

    private String birthday;

    private String adress;

    private boolean isTransporter;

    private String favouriteTransport;

    private boolean isProducer;

    private String linkSocial;

    private boolean isPoint;

    private String openingTimePoint;

    private boolean isDeliveryMan;

    private String openingTime;

    private Set<String> authorities;

    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public AdminUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate().toString();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate().toString();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "AdminUserDTO [activated=" + activated + ", adress=" + adress + ", authorities=" + authorities
                + ", birthday=" + birthday + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", email="
                + email + ", favouriteTransport=" + favouriteTransport + ", firstName=" + firstName + ", id=" + id
                + ", imageUrl=" + imageUrl + ", isDeliveryMan=" + isDeliveryMan + ", isPoint=" + isPoint
                + ", isProducer=" + isProducer + ", isTransporter=" + isTransporter + ", langKey=" + langKey
                + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + ", lastName="
                + lastName + ", linkSocial=" + linkSocial + ", login=" + login + ", nib=" + nib + ", nif=" + nif
                + ", openingTime=" + openingTime + ", openingTimePoint=" + openingTimePoint + ", phoneNumber="
                + phoneNumber + "]";
    }
}
