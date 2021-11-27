package com.radical.iqube.model.entity;

public interface UserEntityBuilder {
    UserEntityBuilder setLogin(String login);
    UserEntityBuilder setPassword(String password);
    UserEntityBuilder setNickName(String nickName);
    UserEntityBuilder setEmail(String email);
    UserEntity build();
}
