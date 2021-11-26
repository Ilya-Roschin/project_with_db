package com.java.training.application.dao;

import com.java.training.application.model.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository {

    List<Entity> findAll(Connection connection, Class<? extends Entity> clazz) throws SQLException;

    void DeleteById(Connection connection, long id, String table, String column) throws SQLException;

    void insert(Connection connection, String data) throws SQLException;

}
