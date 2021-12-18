package com.radical.iqube.model.dao;

import com.radical.iqube.model.entity.UserEntity;

/**
 * Class will be used by "Login/Register" servlet and will be init with same servlet instance
 **/
public class UserDaoService {
    private final UserDaoImpl userDaoImpl;

    public UserDaoService(){this.userDaoImpl = new UserDaoImpl();}
    public boolean createUser(UserEntity user){return userDaoImpl.create(user);}
    public boolean removeUser(UserEntity user){return userDaoImpl.remove(user);}
    public boolean updateLogin(UserEntity user,String value) {
       return userDaoImpl.update(user.getLogin(),"login",value);
    }
    public boolean updatePassword(UserEntity user,String value)
    {
        return userDaoImpl.update(user.getLogin(),"password",value);
    }

    public boolean updateEmail(UserEntity user,String value)
    {
        return userDaoImpl.update(user.getLogin(),"email",value);
    }

    public boolean updateNickname(UserEntity user,String value)
    {
        return userDaoImpl.update(user.getLogin(),"nickname",value);
    }

    public UserEntity getByLogin(String login){return userDaoImpl.get("login",login);}
    public UserEntity getByEmail(String email){return userDaoImpl.get("email",email);}
    public UserEntity getByNickname(String nickname){return userDaoImpl.get("nickname",nickname);}




}
