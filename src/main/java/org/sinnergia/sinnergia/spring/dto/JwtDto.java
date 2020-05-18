package org.sinnergia.sinnergia.spring.dto;

public class JwtDto {

    private String token;

    public JwtDto() {
        // Empty for framework
    }

    public JwtDto(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
