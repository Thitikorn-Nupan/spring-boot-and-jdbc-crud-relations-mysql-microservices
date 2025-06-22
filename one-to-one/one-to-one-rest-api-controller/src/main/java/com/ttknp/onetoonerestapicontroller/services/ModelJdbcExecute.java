package com.ttknp.onetoonerestapicontroller.services;

import com.ttknp.jdbcservice.helpers.jdbc.JdbcExecuteHelper;
import com.ttknp.jdbcservice.helpers.jdbc_select.JdbcSelectExecuteHelper;
import com.ttknp.jdbcservice.helpers.jdbc_update.JdbcUpdateExecuteHelper;
import com.ttknp.onetoonerestapicontroller.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

// ** New concept
public abstract class ModelJdbcExecute<T> {
    // no need CDI in abs class
    @Autowired
    protected JdbcExecuteHelper jdbcExecuteHelper;
    @Autowired
    protected JdbcSelectExecuteHelper jdbcSelectExecuteHelper;
    @Autowired
    protected JdbcUpdateExecuteHelper jdbcUpdateExecuteHelper;
    // Reads
    public abstract List<T> findAll();
    public abstract Set<T> findAllRelationsModels();
    public abstract <U> List<U> findAllOnlyColumn(String columnName);
    public abstract <U> T findOneByPk(U pk);
    // Create
    public abstract Integer saveModel(T model);
    // Update
    public abstract <U> Integer updateModelByPk(T model, U pk);
    // Delete
    public abstract <U> Integer deleteModelByPk( U pk);

}
