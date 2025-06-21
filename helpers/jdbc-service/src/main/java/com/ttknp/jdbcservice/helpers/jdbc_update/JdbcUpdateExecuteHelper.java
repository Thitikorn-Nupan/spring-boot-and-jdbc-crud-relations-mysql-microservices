package com.ttknp.jdbcservice.helpers.jdbc_update;

import com.ttknp.jdbcservice.helpers.annotation.IgnoreGenerateSQL;
import com.ttknp.jdbcservice.helpers.service.UsefulJdbcService;
import com.ttknp.logservice.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class JdbcUpdateExecuteHelper<T> {

    private final static LogService SERVICE = new LogService(JdbcUpdateExecuteHelper.class);

    private final JdbcTemplate jdbcTemplate;
    private final UsefulJdbcService usefulJdbcService;
    private final String INSERT  = "insert into ";
    private final String DELETE  = "delete from ";
    private final String ASSIGN_PARAM  = " = ?";
    private final String ASSIGN  = " ?";

    @Autowired
    public JdbcUpdateExecuteHelper(JdbcTemplate jdbcTemplate, UsefulJdbcService usefulJdbcService) {
        this.jdbcTemplate = jdbcTemplate;
        this.usefulJdbcService = usefulJdbcService;
    }

    // for insert , update , delete
    private Integer executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }


    public Integer insertOne(Class<T> aBeanClass, Object ...params) {
        StringBuilder stringBuilderSQL,stringBuilderSQLValues;

        stringBuilderSQL = new StringBuilder();
        stringBuilderSQLValues = new StringBuilder();

        stringBuilderSQL.append(INSERT);
        stringBuilderSQL.append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        stringBuilderSQL.append(" (");

        stringBuilderSQLValues.append(" values (");

        Field[] fields = aBeanClass.getDeclaredFields(); // get each props as array of class

        for (int i = 0; i < fields.length ; i++) {

            Field field = fields[i];


            boolean foundIgnoreField = field.isAnnotationPresent(IgnoreGenerateSQL.class); // check annotation present on prop

            if (!foundIgnoreField) {

                // Note, Don't forget mark @Column("<real field name>") if it's not the same property name and column name
                Column columnAnnotation = null;
                String fieldName;
                boolean foundCustomField = field.isAnnotationPresent(Column.class);

                if (foundCustomField) {
                    columnAnnotation = field.getAnnotation(Column.class);
                }

                if (columnAnnotation != null) { // check if property name (POJO) it's not the same column name (Field Table)
                    fieldName = columnAnnotation.value();  // columnAnnotation => @org.springframework.data.relational.core.mapping.Column("full_name") columnAnnotation.value() =>  full_name
                }
                else {
                    fieldName = field.getName();
                }

                stringBuilderSQL
                        .append(fieldName)
                        .append(" ,");

                stringBuilderSQLValues
                        .append(ASSIGN)
                        .append(" ,");
            }

            if (i == fields.length - 1) { // remove the last comma
                stringBuilderSQL
                        .deleteCharAt(stringBuilderSQL.length() - 1)
                        .append(")");

                stringBuilderSQLValues
                        .deleteCharAt(stringBuilderSQLValues.length() - 1)
                        .append(")");
            }

        } // end for

        stringBuilderSQL
                .append(stringBuilderSQLValues);

        SERVICE.log.debug("sql insert = {}", stringBuilderSQL.toString());
        return executeUpdate(stringBuilderSQL.toString(),params);
    }


    public Integer deleteOne(Class<T> aBeanClass,String uniqColumnName, Object ...params) {
        StringBuilder stringBuilderSQL = new StringBuilder()
                .append(DELETE)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+uniqColumnName)
                .append(ASSIGN_PARAM);
        return executeUpdate(stringBuilderSQL.toString(),params);
    }



}
