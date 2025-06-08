package com.ttknp.restapicontroller.controllers;

import com.ttknp.restapicontroller.entities.Employee;
import com.ttknp.restapicontroller.services.ModelController;
import com.ttknp.restapicontroller.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController extends ModelController<Employee> {

    private final ModelService<Employee> modelService;

    @Autowired
    public EmployeeController(ModelService<Employee> modelService) {
        this.modelService = modelService;
    }

     @GetMapping(value = "/reads")
    @Override
    public ResponseEntity<List<Employee>> reads() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findAll());
    }

    @GetMapping(value = "/reads-relations")
    @Override
    public ResponseEntity<Set<Employee>> readsRelations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findAllRelationsModels());
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
    public ResponseEntity<Employee> readByPrimaryKey(@PathVariable("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.findOneByPk(pk));
    }

    @GetMapping(value = "/read/relation/{pk}")
    @Override
    public ResponseEntity<Employee> readRelationsByPrimaryKey(@PathVariable("pk") String pk) {
        Employee employee = this.modelService.findOneRelationsModelsByPk(pk);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee.getEid() != null ? employee : null);
    }

    @PostMapping(value = "/create")
    @Override
    public ResponseEntity<Integer> create(@RequestBody Employee model) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.saveModel(model));
    }

    @PostMapping(value = "/create/relation")
    @Override
    public ResponseEntity<Integer> createRelation(@RequestParam("eid") String eid, @RequestParam("aid")String aid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.saveRelationsModels(eid,aid));
    }

    @PutMapping(value = "/update")
    @Override
    public ResponseEntity<Integer> updateByPrimaryKey(@RequestBody Employee model, @RequestParam("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.updateModelByPk(model,pk));
    }


    @DeleteMapping(value = "/delete")
    @Override
    public ResponseEntity<Integer> deleteByPrimaryKey(@RequestParam("pk") String pk) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.modelService.deleteModelByPk(pk));
    }


}
