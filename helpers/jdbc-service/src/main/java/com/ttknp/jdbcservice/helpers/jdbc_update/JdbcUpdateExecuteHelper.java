package com.ttknp.jdbcservice.helpers.jdbc_update;

import com.ttknp.jdbcservice.helpers.annotation.IgnoreGenerateSQL;
import com.ttknp.jdbcservice.helpers.service.UsefulJdbcService;
import com.ttknp.logservice.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcUpdateExecuteHelper<T> {

    private final static LogService SERVICE = new LogService(JdbcUpdateExecuteHelper.class);

    private final JdbcTemplate jdbcTemplate;
    private final UsefulJdbcService usefulJdbcService;
    private final String INSERT  = "insert into ";
    private final String DELETE  = "delete from ";
    private final String UPDATE  = "update ";
    private final String ASSIGN_EQUAL = " = ?";
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

        for (Field field : fields) {

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
                } else {
                    fieldName = field.getName();
                }

                stringBuilderSQL
                        .append(fieldName)
                        .append(" ,");

                stringBuilderSQLValues
                        .append(ASSIGN)
                        .append(" ,");
            }

            /*
            Get bug some cases (case one field)
            if (i == fields.length - 1) { // remove the last comma
                stringBuilderSQL
                        .deleteCharAt(stringBuilderSQL.length() - 1)
                        .append(")");

                stringBuilderSQLValues
                        .deleteCharAt(stringBuilderSQLValues.length() - 1)
                        .append(")");
            }
            */

        } // end for

        stringBuilderSQL
                .deleteCharAt(stringBuilderSQL.length() - 1)
                .append(")");

        stringBuilderSQLValues
                .deleteCharAt(stringBuilderSQLValues.length() - 1)
                .append(")");

        stringBuilderSQL
                .append(stringBuilderSQLValues);

        SERVICE.log.debug("sql insert = {}", stringBuilderSQL.toString());
        return executeUpdate(stringBuilderSQL.toString(),params);
    }

    // ------------ Dynamic insert statement auto map param (*** apply custom annotation) ------------
    public Integer insertOne(Class<T> aBeanClass, T aBeanObject) throws IllegalAccessException {
        StringBuilder stringBuilderSQL,stringBuilderSQLValues;

        stringBuilderSQL = new StringBuilder();
        stringBuilderSQLValues = new StringBuilder();

        stringBuilderSQL.append(INSERT);
        stringBuilderSQL.append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        stringBuilderSQL.append(" (");

        stringBuilderSQLValues.append(" values (");

        Field[] fields = aBeanClass.getDeclaredFields(); // get each props as array of class
        List<Object> objectsValues = new ArrayList<>();

        for (Field field : fields) {

            // !"this$0".equals(field.getName()) it checks for class that declare on inner class
            // Optional
            if (!field.isAnnotationPresent(IgnoreGenerateSQL.class) && !"this$0".equals(field.getName())) {

                // **** Make the field accessible if it's private
                field.setAccessible(true);
                Column columnAnnotation = null;
                String fieldName;
                Object fieldValue;

                if (field.isAnnotationPresent(Column.class)) {
                    columnAnnotation = field.getAnnotation(Column.class);
                }

                if (columnAnnotation != null) { // check if property name (POJO) it's not the same column name (Field Table)
                    fieldName = columnAnnotation.value();  // columnAnnotation => @org.springframework.data.relational.core.mapping.Column("full_name") columnAnnotation.value() =>  full_name
                } else {
                    fieldName = field.getName();
                }

                stringBuilderSQL
                        .append(fieldName)
                        .append(" ,");

                stringBuilderSQLValues
                        .append(ASSIGN)
                        .append(" ,");


                // **** Get the value of the field for the specific POJO instance
                fieldValue = field.get(aBeanObject);
                // log.debug("Field Name: {} , Value: {}" ,fieldName, fieldValue);
                objectsValues.add(fieldValue);
            }

        } // end for

        stringBuilderSQL
                .deleteCharAt(stringBuilderSQL.length() - 1)
                .append(")");

        stringBuilderSQLValues
                .deleteCharAt(stringBuilderSQLValues.length() - 1)
                .append(")");

        stringBuilderSQL
                .append(stringBuilderSQLValues);

        SERVICE.log.debug("sql insert = {}", stringBuilderSQL.toString());
        return executeUpdate(stringBuilderSQL.toString(),objectsValues.toArray());
    }

    public Integer updateOne(Class<T> aBeanClass, String uniqColumnName,Object ...params){

        StringBuilder stringBuilderSQL;

        stringBuilderSQL = new StringBuilder();

        stringBuilderSQL.append(UPDATE);
        stringBuilderSQL.append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        stringBuilderSQL.append(" set ");

        Field[] fields = aBeanClass.getDeclaredFields(); // get each props as array of class

        for (int i = 0; i < fields.length ; i++) {

            Field field = fields[i];

            boolean foundIgnoreField = field.isAnnotationPresent(IgnoreGenerateSQL.class); // check annotation present on prop

            if (!foundIgnoreField ) {

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

                if (!uniqColumnName.equals(fieldName)) {
                    stringBuilderSQL.append(fieldName)
                            .append(ASSIGN_EQUAL)
                            .append(" ,");
                }

                // Get bug some cases if i do where inside loop
            }

        } // end for

        stringBuilderSQL
                .deleteCharAt(stringBuilderSQL.length() - 1)
                .append(" where ")
                .append(uniqColumnName)
                .append(ASSIGN_EQUAL);

        SERVICE.log.debug("sql update = {}", stringBuilderSQL.toString());
        return executeUpdate(stringBuilderSQL.toString(),params);
    }

    // ------------ Dynamic update statement auto map param (*** apply custom annotation) ------------
    public Integer updateOne(Class<T> aBeanClass, String uniqColumnName, T aBeanObject) throws IllegalAccessException {

        StringBuilder stringBuilderSQL;
        stringBuilderSQL = new StringBuilder();

        stringBuilderSQL.append(UPDATE);
        stringBuilderSQL.append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        stringBuilderSQL.append(" set ");

        Field[] fields = aBeanClass.getDeclaredFields(); // get each props as array of class
        List<Object> objectsValues = new ArrayList<>();

        for (Field field : fields) {

            if (!field.isAnnotationPresent(IgnoreGenerateSQL.class)) {

                field.setAccessible(true); // **** Make the field accessible if it's private
                Column columnAnnotation = null;
                String fieldName;
                Object fieldValue;

                if (field.isAnnotationPresent(Column.class)) {
                    columnAnnotation = field.getAnnotation(Column.class);
                }

                if (columnAnnotation != null) { // check if property name (POJO) it's not the same column name (Field Table)
                    fieldName = columnAnnotation.value();  // columnAnnotation => @org.springframework.data.relational.core.mapping.Column("full_name") columnAnnotation.value() =>  full_name
                } else {
                    fieldName = field.getName();
                }

                if (!uniqColumnName.equals(fieldName)) { // uniqColumnName.equals(fieldName) have to do on last element
                    stringBuilderSQL.append(fieldName)
                            .append(ASSIGN_EQUAL)
                            .append(" ,");

                    fieldValue = field.get(aBeanObject); // **** Get the value of the field for the specific POJO instance
                    SERVICE.log.debug("Field Name: {} , Value: {}", fieldName, fieldValue);
                    objectsValues.add(fieldValue);
                }

            }

        } // end for


        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue;
            if (uniqColumnName.equals(fieldName)) {
                field.setAccessible(true); // **** Make the field accessible if it's private
                fieldValue = field.get(aBeanObject); // **** Get the value of the field for the specific POJO instance
                SERVICE.log.debug("Field Name: {} , Value: {}", fieldName, fieldValue);
                objectsValues.add(fieldValue);
            }
        }

        stringBuilderSQL
                .deleteCharAt(stringBuilderSQL.length() - 1)
                .append(" where ")
                .append(uniqColumnName)
                .append(ASSIGN_EQUAL);

        SERVICE.log.debug("sql update = {}", stringBuilderSQL.toString());
        return executeUpdate(stringBuilderSQL.toString(),objectsValues.toArray());
    }


    public Integer deleteOne(Class<T> aBeanClass,String uniqColumnName, Object ...params) {
        StringBuilder stringBuilderSQL = new StringBuilder()
                .append(DELETE)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+uniqColumnName)
                .append(ASSIGN_EQUAL);
        return executeUpdate(stringBuilderSQL.toString(),params);
    }


}
