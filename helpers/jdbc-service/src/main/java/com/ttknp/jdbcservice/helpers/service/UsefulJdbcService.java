package com.ttknp.jdbcservice.helpers.service;

import org.springframework.stereotype.Component;
import org.springframework.data.relational.core.mapping.Table;

@Component
public class UsefulJdbcService {

    public String getTableNameOnTableAnnotation(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            return tableAnnotation.name();
        }
        throw new RuntimeException("Table " + entityClass.getSimpleName() + " has no @Table annotation");
    }

}
