package com.ttknp.onetoonerestapicontroller.dto;

import com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_many.SQLOneToMany;
import com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_one.SQLOneToOne;
import com.ttknp.logservice.LogService;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.services.ModelJdbcExecute;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class EngineDTO extends ModelJdbcExecute<Engine> { // JdbcExecuteHelper jdbcExecuteHelper injects on ModelJdbcExecute abs class

    private final static LogService SERVICE = new LogService(EngineDTO.class);

    @Override
    public List<Engine> findAll() {
        return this.jdbcExecuteHelper.selectAll(Engine.class);
    }

    @Override
    public Set<Engine> findAllRelationsModels() {
        return null;
    }

    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        String sql = String.format(SQLOneToOne.SELECT_ALL_SPECIFY_COLUMN_ENGINES, columnName);
        Class<?> aClass = String.class;
        SERVICE.log.info("sql is {}",sql);
        return jdbcExecuteHelper.selectAllOnlyColumn(Engine.class,(Class<U>) aClass,columnName);
    }

    @Override
    public <U> Engine findOneByPk(U pk) {
        return jdbcExecuteHelper.selectOne(Engine.class,"eid",pk);
    }

    @Override
    public Integer saveModel(Engine model) {
        return jdbcExecuteHelper.insertOne(Engine.class,model);
    }

    @Override
    public <U> Integer updateModelByPk(Engine model, U pk) {
        return 0;
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        return 0;
    }

    public Integer countRowsByColum(String columName,String value) {
        return jdbcExecuteHelper.countRowsByTableAndProperty(Engine.class,columName,value);
    }

}
