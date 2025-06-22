package com.ttknp.onetoonerestapicontroller.dto;


import com.ttknp.logservice.LogService;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.services.ModelJdbcExecute;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

// Done Logic on CarDTO
@Service
public class EngineDTO extends ModelJdbcExecute<Engine> { // JdbcExecuteHelper jdbcExecuteHelper injects on ModelJdbcExecute abs class

    private final static LogService SERVICE = new LogService(EngineDTO.class);

    @Override
    public List<Engine> findAll() {
        return this.jdbcSelectExecuteHelper.selectAll(Engine.class);
    }

    @Override
    public Set<Engine> findAllRelationsModels() {
        return null;
    }

    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        return jdbcSelectExecuteHelper.selectAllOnlyColumn(Engine.class, String.class,columnName);
    }

    @Override
    public <U> Engine findOneByPk(U pk) {
        return (Engine) jdbcSelectExecuteHelper.selectOne(Engine.class,"eid",pk);
    }

    @Override
    public Integer saveModel(Engine model) {
        return 0;
    }

    @Override
    public <U> Integer updateModelByPk(Engine model, U pk) {
        return jdbcUpdateExecuteHelper.updateOne(
                Engine.class,
                "eid",
                model.getCode(),
                pk
                );
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        return 0;
    }

    public Integer countRowsByColum(String columName,String value) {
        return jdbcSelectExecuteHelper.selectCountAllWhere(Engine.class,columName,value);
    }

}
