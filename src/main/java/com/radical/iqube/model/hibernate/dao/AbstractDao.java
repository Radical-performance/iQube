package com.radical.iqube.model.hibernate.dao;

import java.util.Optional;

public interface AbstractDao<T>{

    public Object get();
    public Object findById(int id);
    public boolean removeById(int id);
    public boolean update(Object o);

}


