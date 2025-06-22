package com.ttknp.restapicontroller.dto;

import com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_many.SQLOneToMany;
import com.ttknp.jdbcservice.helpers.jdbc.JdbcExecuteHelper;
import com.ttknp.logservice.LogService;
import com.ttknp.restapicontroller.entities.Address;
import com.ttknp.restapicontroller.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

// Done Logic on EmployeeDTO
@Service
public class AddressDTO implements ModelService<Address> {

    private final JdbcExecuteHelper jdbcExecuteHelper;
    private final static LogService SERVICE = new LogService(AddressDTO.class);

    @Autowired
    public AddressDTO(JdbcExecuteHelper jdbcExecuteHelper) {
        this.jdbcExecuteHelper = jdbcExecuteHelper;
    }

    @Override
    public List<Address> findAll() {
        return jdbcExecuteHelper.selectAll(SQLOneToMany.SELECT_ALL_ADDRESSES, Address.class);
    }

    @Override
    public Set<Address> findAllRelationsModels() {
        return null;
    }

    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        String sql = String.format(SQLOneToMany.SELECT_ALL_SPECIFY_COLUMN_ADDRESSES, columnName);
        // SERVICE.log.info("sql is {}",sql);
        return jdbcExecuteHelper.selectAllOnlyColumn(sql, (Class<U>) String.class);
    }

    @Override
    public Address findOneBy(Object... params) {
        return null;
    }

    @Override
    public <U> Address findOneByPk(U pk) {
        return jdbcExecuteHelper.selectOne(SQLOneToMany.SELECT_ONE_ADDRESSES_BY_PK, Address.class,pk);
    }

    @Override
    public <U> Address findOneRelationsModelsByPk(U pk) {
        return null;
    }

    @Override
    public Integer countAllRows() {
        return jdbcExecuteHelper.countRows(SQLOneToMany.SELECT_COUNT_ADDRESSES);
    }

    @Override
    public Integer saveModel(Address model) {
        return 0;
    }

    @Override
    public <U1, U2> Integer saveRelationsModels(U1 pk1, U2 pk2) {
        return 0;
    }

    @Override
    public <U> Integer updateModelByPk(Address model, U pk) {
        return 0;
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        return 0;
    }


}
