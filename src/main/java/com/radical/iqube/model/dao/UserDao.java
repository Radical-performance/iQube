package com.radical.iqube.model.dao;

import com.radical.iqube.model.entity.UserEntity;

public interface UserDao {
    boolean create(UserEntity user);

    boolean update(String login,String valueName,String value);

    boolean remove(UserEntity user);

    UserEntity get(String valueName,String value);



}
