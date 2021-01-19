package it.uniseats.model;

import java.sql.SQLException;
import java.util.Collection;

public interface ClassModel <T>{
    public T doRetrieveByKey(String code) throws SQLException;

    public Collection<T> doRetrieveAll(String order) throws SQLException;

    public void doSave(T obj) throws SQLException;

    public void doUpdate(T obj) throws SQLException;

    public void doDelete(T obj) throws SQLException;
}
