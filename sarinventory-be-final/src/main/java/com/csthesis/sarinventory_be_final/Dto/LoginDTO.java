package com.csthesis.sarinventory_be_final.Dto;

import com.csthesis.sarinventory_be_final.entities.User;

public class LoginDTO {
    private User user;
    private String jwt;

    public LoginDTO() {
        super();
    }

    public LoginDTO(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
