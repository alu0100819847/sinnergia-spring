package org.sinnergia.sinnergia.spring.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=6)
    private String password;

    @NotNull
    @Size(min=6)
    private String repeatedPassword;

    public UserRegisterDto(){
        this.email = "";
        this.password = "";
        this.repeatedPassword = "";
    }

    public UserRegisterDto(@NotNull String email, @NotNull String password, @NotNull  String repeatedPassword) {
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
