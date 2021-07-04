package com.leb.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.leb.app.domain.UserInfo} entity.
 */
public class UserInfoDTO implements Serializable {

    private Long id;

    private String phoneNumber;

    private String nib;

    private Integer nif;

    private LocalDate birthday;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return address;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nib='" + getNib() + "'" +
            ", nif=" + getNif() +
            ", birthday='" + getBirthday() + "'" +
            ", address='" + getAdress() + "'" +
            "}";
    }
}
