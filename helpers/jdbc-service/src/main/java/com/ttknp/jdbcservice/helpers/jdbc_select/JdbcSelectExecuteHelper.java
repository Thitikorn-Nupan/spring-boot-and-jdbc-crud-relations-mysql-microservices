package com.ttknp.jdbcservice.helpers.jdbc_select;

import com.ttknp.jdbcservice.helpers.service.UsefulJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcSelectExecuteHelper<T> {

    private final JdbcTemplate jdbcTemplate;
    private final UsefulJdbcService usefulJdbcService;
    private final String SELECT_START  = "select * from ";
    private final String SELECT_COUNT  = "select count(*) from ";
    private final String SELECT  = "select ";
    private final String ASSIGN_PARAM  = " = ?";

    @Autowired
    public JdbcSelectExecuteHelper(JdbcTemplate jdbcTemplate, UsefulJdbcService usefulJdbcService) {
        this.jdbcTemplate = jdbcTemplate;
        this.usefulJdbcService = usefulJdbcService;
    }


    // T can be list
    public <T,S> T selectRelation(Class<T> aBeanClass, Class<S> aSubBeanClass, String tableRelationName, String uniqKeyMain, String uniqKeySub, ResultSetExtractor<T> resultSetExtractor) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_START)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass)+" m ")
                .append("JOIN "+tableRelationName+" r ")
                .append("ON r."+uniqKeyMain+" = m."+uniqKeyMain+" ")
                .append("JOIN ")
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aSubBeanClass)+" s ")
                .append("ON r."+uniqKeySub+" = s."+uniqKeySub);
        return jdbcTemplate.query(stringBuilder.toString(), resultSetExtractor);
    }

    public <T,S> T selectRelationWhereMain(Class<T> aBeanClass, Class<S> aSubBeanClass, String tableRelationName, String uniqKeyMain, String uniqKeySub, ResultSetExtractor<T> resultSetExtractor,Object param) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_START)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass)+" m ")
                .append("JOIN "+tableRelationName+" r ")
                .append("ON r."+uniqKeyMain+" = m."+uniqKeyMain+" ")
                .append("JOIN ")
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aSubBeanClass)+" s ")
                .append("ON r."+uniqKeySub+" = s."+uniqKeySub+" ")
                .append("WHERE m."+uniqKeyMain)
                .append(ASSIGN_PARAM);
        return jdbcTemplate.query(stringBuilder.toString(), resultSetExtractor, param);
    }


    // BeanPropertyRowMapper, this class saves you a lot of time for the mapping.
    private <T> List<T> selectAll(String sql, Class<T> aBeanClass) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(aBeanClass));
    }

    // ****  select all for entity that have @Table annotation
    public <T> List<T> selectAll(Class<T> aBeanClass) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_START)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        return selectAll(stringBuilder.toString(), aBeanClass);
    }

    // U can be only String , Integer , ... anything but should not be Object
    public <U> List<U> selectAllOnlyColumn(Class<T> aBeanClass, Class<U> aTypeClass, String columnName ) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT)
                .append(columnName + " from ")
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        return jdbcTemplate.queryForList(stringBuilder.toString(), aTypeClass);
    }


    public <T> T selectOne(Class<T> aBeanClass,String uniqColumnName ,Object param) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_START)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+uniqColumnName)
                .append(ASSIGN_PARAM);
        return jdbcTemplate.queryForObject(stringBuilder.toString(), new BeanPropertyRowMapper<T>(aBeanClass), param);
    }

    public <U> U selectOneOnlyColumn(Class<T> aBeanClass, Class<U> aTypeClass,String columnName ,String uniqColumnName ,Object param) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT)
                .append(columnName + " from ")
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+uniqColumnName)
                .append(ASSIGN_PARAM);
        return jdbcTemplate.queryForObject(stringBuilder.toString(), aTypeClass, param);
    }


    private Integer selectCount(String sql,Object... params) { // Note ... params can be null
        return jdbcTemplate.queryForObject(sql,Integer.class,params);
    }

    public Integer selectCountAll(Class<T> aBeanClass){
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_COUNT)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass));
        return selectCount(stringBuilder.toString());
    }

    public Integer selectCountAllWhere(Class<T> aBeanClass, String columnName , Object param){
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_COUNT)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+columnName)
                .append(ASSIGN_PARAM);
        return selectCount(stringBuilder.toString(),param);
    }

    public Integer selectCountAllWhereLike(Class<T> aBeanClass, String columnName , Object param){
        StringBuilder stringBuilder = new StringBuilder()
                .append(SELECT_COUNT)
                .append(usefulJdbcService.getTableNameOnTableAnnotation(aBeanClass))
                .append(" where "+columnName+" like ?"); // ? have to be like %<KEY>% or %<KEY> or <KEY>%
        return selectCount(stringBuilder.toString(),param);
    }



}
