package com.ttknp.jdbcservice.helpers.jdbc;

import com.ttknp.jdbcservice.helpers.service.UsefulJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcExecuteHelper {

    private final JdbcTemplate jdbcTemplate;
    private final UsefulJdbcService usefulJdbcService;

    @Autowired
    public JdbcExecuteHelper(JdbcTemplate jdbcTemplate, UsefulJdbcService usefulJdbcService) {
        this.jdbcTemplate = jdbcTemplate;
        this.usefulJdbcService = usefulJdbcService;
    }

    // BeanPropertyRowMapper, this class saves you a lot of time for the mapping.
    public <T> List<T> selectAll(String sql, Class<T> aBeanClass) {
        // var rowMapper = BeanPropertyRowMapper.newInstance(aBeanClass); // auto map getter/setter
        // return jdbcTemplate.query(sql,rowMapper);
        // can reduce by below
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(aBeanClass));
    }

    // ****  select all for entity that have @Table annotation
    public <T> List<T> selectAll(Class<T> aBeanClass) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from " + usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        return selectAll(stringBuilder.toString(), aBeanClass);
    }

    // U can be only String , Integer , ... anything but should not be Object
    public <U> List<U> selectAllOnlyColumn(String sql, Class<U> aClass) {
        return jdbcTemplate.queryForList(sql, aClass);
    }

    // **** select all only column for entity that have @Table annotation
    public <U> List<U> selectAllOnlyColumn(Class<?> aEntityClass,Class<U> aTypeClass, String columName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select "+columName+" from " + usefulJdbcService.getTableNameOnTableAnnotation(aEntityClass));
        return selectAllOnlyColumn(stringBuilder.toString(), aTypeClass);
    }

    public <T> T selectOne(String sql, Class<T> aBeanClass, Object... params) {
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(aBeanClass), params);
    }

    // **** select one for entity that have @Table annotation
    public <T> T selectOne(Class<T> aBeanClass, String columPkName,Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from " + usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        stringBuilder.append(" where "+columPkName+" = ?");
        return selectOne(stringBuilder.toString(), aBeanClass, params);
    }

    public <T> T selectRelation(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params) {
        return jdbcTemplate.query(sql, resultSetExtractor, params);
    }

    public Integer countRows(String sql){
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public Integer countRows(String sql,Object... params) {
        return jdbcTemplate.queryForObject(sql,Integer.class,params);
    }

    // **** select count for entity that have @Table annotation
    public Integer countRowsByTableAndProperty(Class<?> entityClass,String columName,Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(*) from ");
        String tableName = usefulJdbcService.getTableNameOnTableAnnotation(entityClass);
        stringBuilder.append(tableName+" where "+columName+" = ?");
        return countRows(stringBuilder.toString(),params);
    }

    // **** select count for entity that have @Table annotation
    /**
        ex: LIKE %ALL NEW YARIS ATIV%, is selects count all cars with a tableName that have "ALL NEW YARIS ATIV" in any position
    */
    public Integer countRowsLikeByTableAndProperty(Class<?> entityClass,String columName,Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(*) from ");
        String tableName = usefulJdbcService.getTableNameOnTableAnnotation(entityClass);
        stringBuilder.append(tableName+" where "+columName+" like ?");
        return countRows(stringBuilder.toString(),params);
    }

    public Integer insertOne(String statement, Object... params) { // Object... theValues can be (sql,1,"1",'1',true) or null (sql)
        return executeUpdate(statement, params);
    }

    // **** insert without statement
    public <T> Integer insertOne(Class<T> entityClass,T entity) {
        if (jdbcTemplate.getDataSource() != null) {
            final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
            String tableName = usefulJdbcService.getTableNameOnTableAnnotation(entityClass);
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
            simpleJdbcInsert.withTableName(tableName);
            return simpleJdbcInsert.execute(parameters);
        }
        return 0;
    }

    public Integer update(String statement, Object... params) { //
        return executeUpdate(statement, params);
    }

    public Integer delete(String statement, Object... params) {
        return executeUpdate(statement, params);
    }

    private Integer executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }



}
