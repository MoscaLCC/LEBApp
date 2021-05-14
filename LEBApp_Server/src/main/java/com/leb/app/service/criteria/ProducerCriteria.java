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
 * Criteria class for the {@link com.leb.app.domain.Producer} entity. This class is used
 * in {@link com.leb.app.web.rest.ProducerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /producers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProducerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter mail;

    private StringFilter phoneNumber;

    private StringFilter nib;

    private IntegerFilter nif;

    private LocalDateFilter birthday;

    private StringFilter adress;

    private StringFilter photo;

    private StringFilter linkSocial;

    private IntegerFilter numberRequests;

    private DoubleFilter payedValue;

    private DoubleFilter valueToPay;

    private DoubleFilter ranking;

    private LongFilter requestId;

    public ProducerCriteria() {}

    public ProducerCriteria(ProducerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nib = other.nib == null ? null : other.nib.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.adress = other.adress == null ? null : other.adress.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.linkSocial = other.linkSocial == null ? null : other.linkSocial.copy();
        this.numberRequests = other.numberRequests == null ? null : other.numberRequests.copy();
        this.payedValue = other.payedValue == null ? null : other.payedValue.copy();
        this.valueToPay = other.valueToPay == null ? null : other.valueToPay.copy();
        this.ranking = other.ranking == null ? null : other.ranking.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
    }

    @Override
    public ProducerCriteria copy() {
        return new ProducerCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getMail() {
        return mail;
    }

    public StringFilter mail() {
        if (mail == null) {
            mail = new StringFilter();
        }
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
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

    public StringFilter getPhoto() {
        return photo;
    }

    public StringFilter photo() {
        if (photo == null) {
            photo = new StringFilter();
        }
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public StringFilter getLinkSocial() {
        return linkSocial;
    }

    public StringFilter linkSocial() {
        if (linkSocial == null) {
            linkSocial = new StringFilter();
        }
        return linkSocial;
    }

    public void setLinkSocial(StringFilter linkSocial) {
        this.linkSocial = linkSocial;
    }

    public IntegerFilter getNumberRequests() {
        return numberRequests;
    }

    public IntegerFilter numberRequests() {
        if (numberRequests == null) {
            numberRequests = new IntegerFilter();
        }
        return numberRequests;
    }

    public void setNumberRequests(IntegerFilter numberRequests) {
        this.numberRequests = numberRequests;
    }

    public DoubleFilter getPayedValue() {
        return payedValue;
    }

    public DoubleFilter payedValue() {
        if (payedValue == null) {
            payedValue = new DoubleFilter();
        }
        return payedValue;
    }

    public void setPayedValue(DoubleFilter payedValue) {
        this.payedValue = payedValue;
    }

    public DoubleFilter getValueToPay() {
        return valueToPay;
    }

    public DoubleFilter valueToPay() {
        if (valueToPay == null) {
            valueToPay = new DoubleFilter();
        }
        return valueToPay;
    }

    public void setValueToPay(DoubleFilter valueToPay) {
        this.valueToPay = valueToPay;
    }

    public DoubleFilter getRanking() {
        return ranking;
    }

    public DoubleFilter ranking() {
        if (ranking == null) {
            ranking = new DoubleFilter();
        }
        return ranking;
    }

    public void setRanking(DoubleFilter ranking) {
        this.ranking = ranking;
    }

    public LongFilter getRequestId() {
        return requestId;
    }

    public LongFilter requestId() {
        if (requestId == null) {
            requestId = new LongFilter();
        }
        return requestId;
    }

    public void setRequestId(LongFilter requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProducerCriteria that = (ProducerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(nib, that.nib) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(adress, that.adress) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(linkSocial, that.linkSocial) &&
            Objects.equals(numberRequests, that.numberRequests) &&
            Objects.equals(payedValue, that.payedValue) &&
            Objects.equals(valueToPay, that.valueToPay) &&
            Objects.equals(ranking, that.ranking) &&
            Objects.equals(requestId, that.requestId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            mail,
            phoneNumber,
            nib,
            nif,
            birthday,
            adress,
            photo,
            linkSocial,
            numberRequests,
            payedValue,
            valueToPay,
            ranking,
            requestId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProducerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (mail != null ? "mail=" + mail + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (nib != null ? "nib=" + nib + ", " : "") +
            (nif != null ? "nif=" + nif + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (adress != null ? "adress=" + adress + ", " : "") +
            (photo != null ? "photo=" + photo + ", " : "") +
            (linkSocial != null ? "linkSocial=" + linkSocial + ", " : "") +
            (numberRequests != null ? "numberRequests=" + numberRequests + ", " : "") +
            (payedValue != null ? "payedValue=" + payedValue + ", " : "") +
            (valueToPay != null ? "valueToPay=" + valueToPay + ", " : "") +
            (ranking != null ? "ranking=" + ranking + ", " : "") +
            (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }
}
