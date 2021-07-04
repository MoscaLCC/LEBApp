package com.leb.app.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.leb.app.domain.Dimensions} entity.
 */
public class LoginDTO implements Serializable {

    private String token;
    
    private String firstName;

    private String lastName;
    
    private List<String> profiles;


    public LoginDTO() {
        super();
    }

    public LoginDTO(String token, String firstName, String lastName, List<String> profiles) {
        super();
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profiles = profiles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profils) {
        this.profiles = profils;
    }

    @Override
    public String toString() {
        return "LoginDTO [firstName=" + firstName + ", lastName=" + lastName + ", profils=" + profiles + ", token="
                + token + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((profiles == null) ? 0 : profiles.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        LoginDTO other = (LoginDTO) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (profiles == null) {
            if (other.profiles != null)
                return false;
        } else if (!profiles.equals(other.profiles))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }

    

}
