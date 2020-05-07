package org.sinnergia.sinnergia.spring.dto;

import javax.validation.constraints.NotNull;

public class UserLandingDto {

    @NotNull
    private String email;

    @NotNull
    private String roles;

    public UserLandingDto(@NotNull String email, @NotNull String roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserLandingDto{" +
                "email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
