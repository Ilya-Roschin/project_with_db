package com.java.training.application.dao;

import com.java.training.application.model.Entity;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T extends Entity> {

    List<T> findAll() throws SQLException;

    void deleteById(long id, String table, String column) throws SQLException;

    void insert(String data) throws SQLException;

}
