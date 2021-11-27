package com.radical.iqube.model.entity;

public class UserEntityBuilderImpl implements UserEntityBuilder {
    private final UserEntity user = new UserEntity();

    @Override
    public UserEntityBuilder setLogin(String login) {user.login = login;return this;}

    @Override
    public UserEntityBuilder setPassword(String password) {user.password = password;return this;}

    @Override
    public UserEntityBuilder setNickName(String nickName) {user.nickname = nickName;return this;}

    @Override
    public UserEntityBuilder setEmail(String email) {user.email = email;return this;}

    @Override
    public UserEntity build() {
        return user;
    }
}
