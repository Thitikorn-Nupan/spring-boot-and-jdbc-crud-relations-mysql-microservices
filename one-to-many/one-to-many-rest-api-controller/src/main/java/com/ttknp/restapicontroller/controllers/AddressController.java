package com.ttknp.restapicontroller.controllers;

import com.ttknp.restapicontroller.entities.Address;
import com.ttknp.restapicontroller.services.ModelController;
import com.ttknp.restapicontroller.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/address")
public class AddressController extends ModelController<Address> {

    private final ModelService<Address> modelService;

    @Autowired
    public AddressController(ModelService<Address> modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/reads")
    @Override
    public ResponseEntity<List<Address>> reads() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findAll());
    }


    @GetMapping(value = "/reads/{columnName}") // in jdk 17 have to specify path var name
    @Override
    public ResponseEntity<List<Object>> readsSpecifyColumn(@PathVariable("columnName") String columnName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findAllOnlyColumn(columnName));
    }

    @GetMapping(value = "/reads/count")
    @Override
    public ResponseEntity<Integer> readsCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.countAllRows());
    }

    @GetMapping(value = "/read/{pk}")
    @Override
    public ResponseEntity<Address> readByPrimaryKey(@PathVariable("pk")  String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findOneByPk(pk));
    }


    @Override
    public ResponseEntity<Set<Address>> readsRelations() {
        return null;
    }

    @Override
    public ResponseEntity<Address> readRelationsByPrimaryKey(String pk) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> create(Address model) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> createRelation(String eid, String aid) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> updateByPrimaryKey(Address model, String pk) {
        return null;
    }

    @Override
    public ResponseEntity<Integer> deleteByPrimaryKey(String pk) {
        return null;
    }
}
