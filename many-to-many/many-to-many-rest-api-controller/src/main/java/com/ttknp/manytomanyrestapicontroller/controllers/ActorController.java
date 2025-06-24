package com.ttknp.manytomanyrestapicontroller.controllers;

import com.ttknp.manytomanyrestapicontroller.custom_annotations.CommonRestAPI;
import com.ttknp.manytomanyrestapicontroller.dto.ActorDTO;
import com.ttknp.manytomanyrestapicontroller.entities.Actor;
import com.ttknp.manytomanyrestapicontroller.services.ModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CommonRestAPI(configPath = {"/actor","actors"})
public class ActorController extends ModelController<Actor> {

    private final ActorDTO actorDTO;

    @Autowired
    public ActorController(ActorDTO actorDTO) {
        this.actorDTO = actorDTO;
    }

    @Override
    protected ResponseEntity<List<Actor>> reads() {
        return ResponseEntity.ok(actorDTO.findAll());
    }

    @Override
    protected ResponseEntity<Set<Actor>> readsRelations() {
        return null;
    }

    @Override
    protected ResponseEntity<List<Object>> readsSpecifyColumn(@PathVariable("columnName") String columnName) {
        return ResponseEntity.ok(actorDTO.findAllOnlyColumn(columnName));
    }

    @Override
    protected ResponseEntity<Actor> readByPrimaryKey(@PathVariable("pk") String pk) {
        return ResponseEntity.ok(actorDTO.findOneByPk(pk));
    }

    @GetMapping(value = "/read-relation/{pk}")
    protected ResponseEntity<Actor> readRelationByPrimaryKey(@PathVariable("pk") String pk) {
        return ResponseEntity.ok(actorDTO.findOneRelationByPk(pk));
    }

    @Override
    protected ResponseEntity<Integer> create(@RequestBody Actor model) {
        return ResponseEntity.ok(actorDTO.saveModel(model));
    }

    @PostMapping(value = "/create-relation")
    protected ResponseEntity<Integer> createRelation(@RequestParam("aid") String aid , @RequestParam("mid") String mid) {
        return ResponseEntity.ok(actorDTO.saveRelationModel(aid,mid));
    }

    @Override
    protected ResponseEntity<Integer> updateByPrimaryKey(@RequestBody Actor model, @RequestParam("pk") String pk) {
        return ResponseEntity.ok(actorDTO.updateModelByPk(model,pk));
    }

    @Override
    protected ResponseEntity<Integer> deleteByPrimaryKey(@RequestParam("pk") String pk) {
        return ResponseEntity.ok(actorDTO.deleteModelByPk(pk));
    }

}
