package com.ttknp.restapicontroller.dto;

import com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_many.SQLOneToMany;
import com.ttknp.jdbcservice.helpers.jdbc.JdbcExecuteHelper;
import com.ttknp.logservice.LogService;
import com.ttknp.restapicontroller.entities.Address;
import com.ttknp.restapicontroller.entities.Employee;
import com.ttknp.restapicontroller.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Done Logic query by writing sql statements
@Service
public class EmployeeDTO implements ModelService<Employee> {

    private final JdbcExecuteHelper jdbcExecuteHelper;
    private final static LogService SERVICE = new LogService(EmployeeDTO.class);

    @Autowired
    public EmployeeDTO(JdbcExecuteHelper jdbcExecuteHelper) {
        this.jdbcExecuteHelper = jdbcExecuteHelper;
    }


    @Override
    public List<Employee> findAll() {
        return jdbcExecuteHelper.selectAll(SQLOneToMany.SELECT_ALL_EMPLOYEES, Employee.class);
    }


    @Override
    public Set<Employee> findAllRelationsModels() {
        return jdbcExecuteHelper.selectRelation(SQLOneToMany.SELECT_ALL_EMPLOYEES_ADDRESSES,new EmployeesJoinAddressesResultSetExtractor());
    }


    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        String sql = String.format(SQLOneToMany.SELECT_ALL_SPECIFY_COLUMN_EMPLOYEES, columnName);
        Class<?> aClass ;
        switch (columnName) {
            case "active": aClass = Boolean.class;
            break;
            case "salary": aClass = BigDecimal.class;
            break;
            default: aClass = String.class;
            break;
        }
        // SERVICE.log.info("sql is {}",sql);
        return jdbcExecuteHelper.selectAllOnlyColumn(sql, (Class<U>) aClass);
    }


    @Override
    public Employee findOneBy(Object... params) {
        return null;
    }


    @Override
    public <U> Employee findOneByPk(U pk) {
        return jdbcExecuteHelper.selectOne(SQLOneToMany.SELECT_ONE_EMPLOYEES_BY_PK, Employee.class,pk);
    }


    /**
        if you see what return on SELECT_ONE_EMPLOYEES_ADDRESSES_BY_EMPLOYEES_PK
        it returns same employee record ,if employee has more 1 addresses
        So the good way to map try using ResultSetExtractor as manual mapping
    */
    @Override
    public <U> Employee findOneRelationsModelsByPk(U pk) {
        return jdbcExecuteHelper.selectRelation(SQLOneToMany.SELECT_ONE_EMPLOYEES_ADDRESSES_BY_EMPLOYEES_PK,new EmployeeJoinAddressesResultSetExtractor(),pk);
    }


    @Override
    public Integer countAllRows() {
        return jdbcExecuteHelper.countRows(SQLOneToMany.SELECT_COUNT_EMPLOYEES);
    }


    @Override
    public Integer saveModel(Employee model) {
        return jdbcExecuteHelper.insertOne(SQLOneToMany.INSERT_ONE_EMPLOYEES,
                model.getEid(),
                model.getFirstname(),
                model.getLastname(),
                model.getPosition(),
                model.isActive(),
                model.getSalary()
                );
    }


    @Override
    public <U1, U2> Integer saveRelationsModels(U1 pk1, U2 pk2) {
        // should be validate first !
        return jdbcExecuteHelper.insertOne(SQLOneToMany.INSERT_ONE_RELATIONS,
                pk1.toString(),
                pk2.toString()
        );
    }


    @Override
    public <U> Integer updateModelByPk(Employee model, U pk) {
        return jdbcExecuteHelper.update(
                SQLOneToMany.UPDATE_ONE_EMPLOYEES,
                model.getFirstname(),
                model.getLastname(),
                model.getPosition(),
                model.isActive(),
                model.getSalary(),
                pk.toString()
        );
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        if (jdbcExecuteHelper.countRows(SQLOneToMany.SELECT_COUNT_RELATIONS_BY_EMPLOYEES_PK,pk) > 0) { // check relations
            SERVICE.log.debug("Delete relations affected {}",jdbcExecuteHelper.delete(SQLOneToMany.DELETE_RELATIONS_BY_EMPLOYEES_PK,pk)); // remove relations
        }
        SERVICE.log.debug("Delete without relation");
        return jdbcExecuteHelper.delete(SQLOneToMany.DELETE_ONE_EMPLOYEES,pk);
    }


    // This way works if you set Override equals() and hashCode() on your POJOs
    private static class EmployeeJoinAddressesResultSetExtractor implements ResultSetExtractor<Employee> {
        @Override
        public Employee extractData(ResultSet rs) throws SQLException {
            Employee employee = new Employee();
            List<Address> addresses = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString("aid") != null) {
                    employee.setEid(rs.getString("eid"));
                    employee.setFirstname(rs.getString("firstname"));
                    employee.setLastname(rs.getString("lastname"));
                    employee.setPosition(rs.getString("position"));
                    employee.setActive(rs.getBoolean("active"));
                    employee.setSalary(rs.getBigDecimal("salary"));
                    // SERVICE.log.debug("found aid {}", rs.getString("aid"));
                    Address address = new Address();
                    address.setAid(rs.getString("aid"));
                    address.setCountry(rs.getString("country"));
                    address.setCity(rs.getString("city"));
                    address.setDetails(rs.getString("details"));
                    addresses.add(address);
                } else {
                    break;
                }
            }
            employee.setAddresses(addresses);
            return employee;
        }
    }

    private static class EmployeesJoinAddressesResultSetExtractor implements ResultSetExtractor<Set<Employee>> {
        @Override
        public Set<Employee> extractData(ResultSet rs) throws SQLException {
            List<Address> addresses = new ArrayList<>();
            Set<Employee> employeesAsSet = new HashSet<>(); // Note set won't cut the duplicate object if you forget set Override equals() and hashCode() on your POJOs

            while (rs.next()) {
                
                Employee employee = new Employee(
                        rs.getString("eid"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("position"),
                        rs.getBoolean("active"),
                        rs.getBigDecimal("salary")
                );

                employee.setAddresses(new ArrayList<>()); // set addresses as empty element and add each address later
                employeesAsSet.add(employee); // if it's duplicate java pojo it won't add

                Address address = new Address();
                address.setAid(rs.getString("aid"));
                address.setEid(employee.getEid()); // for compare with employee
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
                address.setDetails(rs.getString("details"));

                addresses.add(address);

            }


            // logic for add addresses to employee
            employeesAsSet.forEach(employee -> {

                addresses.forEach(address -> {

                    if (employee.getEid().equals(address.getEid())) {
                        employee.getAddresses().add(address);
                    }

                });

            });

            return employeesAsSet;
        }
    }

}
