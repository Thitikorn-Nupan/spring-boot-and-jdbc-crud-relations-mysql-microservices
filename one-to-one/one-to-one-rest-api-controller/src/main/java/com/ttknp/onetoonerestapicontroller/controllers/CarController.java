package com.ttknp.onetoonerestapicontroller.controllers;

import com.ttknp.onetoonerestapicontroller.custom_annotations.CommonRestAPI;
import com.ttknp.onetoonerestapicontroller.entities.Car;
import com.ttknp.onetoonerestapicontroller.dto.CarDTO;
import com.ttknp.onetoonerestapicontroller.services.ModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@CommonRestAPI(configPath = {"/car","cars"})
public class CarController extends ModelController<Car> {

    private final CarDTO carDTO;

    @Autowired
    public CarController(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    // Very amazing
    // *** You see you can specify @*Mapping on ModelController
    @Override
    protected ResponseEntity<List<Car>> reads() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findAll());
    }

    @Override
    protected ResponseEntity<Set<Car>> readsRelations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findAllRelationsModels());
    }

    @GetMapping(value = "/reads-relations-test") // dynamic sql
    protected ResponseEntity<Set<Car>> readsRelationsTest() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findAllRelationsModelsTest());
    }


    @GetMapping(value = "/reads-relations-where-test") // dynamic sql
    protected ResponseEntity<Set<Car>> readsRelationsWhereTest(@RequestParam("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findAllRelationsModelsWhereTest(pk));
    }

    @Override
    protected ResponseEntity<List<Object>> readsSpecifyColumn(@PathVariable("columnName") String columnName) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findAllOnlyColumn(columnName));
    }

    @Override
    protected ResponseEntity<Car> readByPrimaryKey(@PathVariable("pk") String pk) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.findOneByPk(pk));
    }

    @Override
    protected ResponseEntity<Integer> create(@RequestBody Car car) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.saveModel(car));
    }

    @Override
    protected ResponseEntity<Integer> updateByPrimaryKey(@RequestBody Car model,  @RequestParam("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.updateModelByPk(model,pk));
    }

    @Override
    protected ResponseEntity<Integer> deleteByPrimaryKey(@RequestParam("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.deleteModelByPk(pk));
    }

    @GetMapping(value = "/read/count/by")
    protected ResponseEntity<Integer> countRowsByColumn(@RequestParam("columnName") String columnName , @RequestParam("value") String value) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.carDTO.countRowsByColum(columnName,value));
    }

}


