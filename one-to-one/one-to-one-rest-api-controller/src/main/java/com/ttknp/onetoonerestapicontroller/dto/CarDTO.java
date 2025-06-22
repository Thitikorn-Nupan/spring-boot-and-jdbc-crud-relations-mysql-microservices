package com.ttknp.onetoonerestapicontroller.dto;

import com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_one.SQLOneToOne;
import com.ttknp.logservice.LogService;
import com.ttknp.onetoonerestapicontroller.entities.Car;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.services.ModelJdbcExecute;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Done Logic
@Service
public class CarDTO extends ModelJdbcExecute<Car> { // JdbcExecuteHelper jdbcExecuteHelper injects on ModelJdbcExecute abs class

    private final static LogService SERVICE = new LogService(CarDTO.class);

    @Override
    public List<Car> findAll() {
        return this.jdbcSelectExecuteHelper.selectAll(Car.class);
    }

    @Override
    public Set<Car> findAllRelationsModels() {
        return jdbcExecuteHelper.selectRelation(SQLOneToOne.SELECT_ALL_CARS_ENGINES,new CarsJoinEnginesResultSetExtractor()); // write sql statement on own
    }

    public Set<Car> findAllRelationsModelsTest() { // dynamic sql statement
        return (Set<Car>) jdbcSelectExecuteHelper.selectRelation(Car.class, Engine.class,"cars_engines","cid","eid",new CarsJoinEnginesResultSetExtractor());
    }

    public Set<Car> findAllRelationsModelsWhereTest(String cid) {
        return (Set<Car>) jdbcSelectExecuteHelper.selectRelationWhereMain(Car.class, Engine.class,"cars_engines","cid","eid",new CarsJoinEnginesResultSetExtractor(),cid);
    }


    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        String sql = String.format(SQLOneToOne.SELECT_ALL_SPECIFY_COLUMN_CARS, columnName);
        Class<?> aClass ;
        switch (columnName) {
            case "price": aClass = BigDecimal.class;
                break;
            case "release_date": aClass = LocalDate.class;
                break;
            default: aClass = String.class; // all is string
                break;
        }
        SERVICE.log.info("sql is {}",sql);
        return jdbcSelectExecuteHelper.selectAllOnlyColumn(Car.class,aClass,columnName);
    }

    @Override
    public <U> Car findOneByPk(U pk) {
        return (Car) jdbcSelectExecuteHelper.selectOne(Car.class,"cid",pk);
    }

    @Override
    public Integer saveModel(Car model) {
        return jdbcUpdateExecuteHelper.insertOne(Car.class,
                model.getCid(),
                model.getBrand(),
                model.getModel(),
                model.getPrice(),
                model.getReleaseDate()
                );
    }

    @Override
    public <U> Integer updateModelByPk(Car model, U pk) { // ***
        return jdbcUpdateExecuteHelper.updateOne(
                Car.class,
                "cid",
                model.getBrand(),
                model.getModel(),
                model.getPrice(),
                model.getReleaseDate(),
                pk.toString()
        );
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        if (jdbcSelectExecuteHelper.selectCountAllWhere(Car.class,"cid",pk) > 0) { // check relations
            // just for converting to table name
            @Table(name="cars_engines")
            class CarsEngines {
                CarsEngines(){
                }
            }
            SERVICE.log.debug("Delete relations affected {}",jdbcUpdateExecuteHelper.deleteOne(CarsEngines.class,"cid",pk)); // remove relations
        }
        SERVICE.log.debug("Delete without relation");
        return jdbcUpdateExecuteHelper.deleteOne(Car.class,"cid",pk);
    }


    public Integer countRowsByColum(String columName,String value) {
        return jdbcSelectExecuteHelper.selectCountAllWhere(Car.class,columName,value); // count where
    }

    private static class CarsJoinEnginesResultSetExtractor implements ResultSetExtractor<Set<Car>> {
        @Override
        public Set<Car> extractData(ResultSet rs) throws SQLException {
            Set<Car> carsAsSet = new HashSet<>();

            while (rs.next()) {

                // cid, String brand, String model, BigDecimal price, LocalDate releaseDate
                carsAsSet.add(new Car(
                        rs.getString("cid") ,
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getBigDecimal("price"),
                        rs.getDate("release_date").toLocalDate()
                    ).setEngine(new Engine(
                        rs.getString("eid") ,
                        rs.getString("code")
                        )
                    )
                );


            }

            return carsAsSet;
        }
    }
}
