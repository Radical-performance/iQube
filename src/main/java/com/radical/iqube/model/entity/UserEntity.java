package com.radical.iqube.model.entity;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class UserEntity {
    protected String login;
    protected String password;
    protected String email;
    protected String nickname;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}



