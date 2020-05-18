package org.sinnergia.sinnergia.spring.dto;

import org.sinnergia.sinnergia.spring.documents.Role;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class UserLandingDto {

    @NotNull
    private String email;

    @NotNull
    private Role[] roles;

    public UserLandingDto(@NotNull String email, @NotNull Role[] roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserLandingDto{" +
                "email='" + email + '\'' +
                ", roles='" + Arrays.toString(roles) + '\'' +
                '}';
    }
}
