package com.medicine.medicineapp.repo;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T,I> {

    public T insert(T data) throws SQLException;

    public T update(T data) throws SQLException;

    public T delete(T data) throws SQLException;

    public T getById(I id) throws SQLException;

    public List<T> getAll() throws SQLException;

    public List<T> getAllByField(String fieldName, Object value) throws SQLException;
    
}
