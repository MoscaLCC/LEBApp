package com.leb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.leb.app.domain.UserInfo} entity. This class is used
 * in {@link com.leb.app.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumber;

    private StringFilter nib;

    private IntegerFilter nif;

    private LocalDateFilter birthday;

    private StringFilter adress;

    public UserInfoCriteria() {}

    public UserInfoCriteria(UserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.adress = other.adress == null ? null : other.adress.copy();
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getNib() {
        return nib;
    }

    public StringFilter nib() {
        if (nib == null) {
            nib = new StringFilter();
        }
        return nib;
    }

    public void setNib(StringFilter nib) {
        this.nib = nib;
    }

    public IntegerFilter getNif() {
        return nif;
    }

    public IntegerFilter nif() {
        if (nif == null) {
            nif = new IntegerFilter();
        }
        return nif;
    }

    public void setNif(IntegerFilter nif) {
        this.nif = nif;
    }

    public LocalDateFilter getBirthday() {
        return birthday;
    }

    public LocalDateFilter birthday() {
        if (birthday == null) {
            birthday = new LocalDateFilter();
        }
        return birthday;
    }

    public void setBirthday(LocalDateFilter birthday) {
        this.birthday = birthday;
    }

    public StringFilter getAdress() {
        return adress;
    }

    public StringFilter adress() {
        if (adress == null) {
            adress = new StringFilter();
        }
        return adress;
    }

    public void setAdress(StringFilter adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(adress, that.adress)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, nib, nif, birthday, adress);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (adress != null ? "adress=" + adress + ", " : "") +
            "}";
    }
}