package org.sinnergia.sinnergia.spring.dto;

import org.sinnergia.sinnergia.spring.documents.Role;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UserAdminDto {

    private String id;

    private String email;

    private String name;

    private String surname;

    private Role[] roles;

    private LocalDateTime registrationDate;

    public UserAdminDto(){
        // Empty
    }

    public UserAdminDto(String id, String email, String name, String surname, Role[] roles, LocalDateTime registrationDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Role[] getRoles() {
        return roles;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "UserAdminDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
