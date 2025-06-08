package com.ttknp.onetoonerestapicontroller.controllers;

import com.ttknp.onetoonerestapicontroller.custom_annotations.CommonRestAPI;
import com.ttknp.onetoonerestapicontroller.dto.CarDTO;
import com.ttknp.onetoonerestapicontroller.dto.EngineDTO;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.entities.Engine;
import com.ttknp.onetoonerestapicontroller.services.ModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@CommonRestAPI(configPath = {"/engine","engines"})
public class EngineController extends ModelController<Engine> {

    private final EngineDTO engineDTO;

    @Autowired
    public EngineController(EngineDTO engineDTO) {
        this.engineDTO = engineDTO;
    }

    // Very amazing
    // *** You see you can specify @*Mapping on ModelController
    @Override
    protected ResponseEntity<List<Engine>> reads() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.engineDTO.findAll());
    }

    @Override
    protected ResponseEntity<Set<Engine>> readsRelations() {
        return null;
    }

    @Override
    protected ResponseEntity<List<Object>> readsSpecifyColumn(@PathVariable("columnName") String columnName) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.engineDTO.findAllOnlyColumn(columnName));
    }

    @Override
    protected ResponseEntity<Engine> readByPrimaryKey(@PathVariable("pk") String pk) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.engineDTO.findOneByPk(pk));
    }

    @Override
    protected ResponseEntity<Integer> create(Engine model) {
        return null;
    }

    @Override
    protected ResponseEntity<Integer> updateByPrimaryKey(Engine model, String pk) {
        return null;
    }

    @Override
    protected ResponseEntity<Integer> deleteByPrimaryKey(String pk) {
        return null;
    }

    @GetMapping(value = "/read/count/by")
    protected ResponseEntity<Integer> countRowsByColumn(@RequestParam("columnName") String columnName , @RequestParam("value") String value) { // in jdk 17 have to specify path var name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.engineDTO.countRowsByColum(columnName,value));
    }

}


