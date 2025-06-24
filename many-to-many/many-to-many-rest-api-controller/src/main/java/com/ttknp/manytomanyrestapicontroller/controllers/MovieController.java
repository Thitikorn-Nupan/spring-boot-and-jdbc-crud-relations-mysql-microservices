package com.ttknp.manytomanyrestapicontroller.controllers;

import com.ttknp.manytomanyrestapicontroller.custom_annotations.CommonRestAPI;
import com.ttknp.manytomanyrestapicontroller.dto.MovieDTO;
import com.ttknp.manytomanyrestapicontroller.entities.Movie;
import com.ttknp.manytomanyrestapicontroller.entities.Movie;
import com.ttknp.manytomanyrestapicontroller.services.ModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@CommonRestAPI(configPath = {"/movie","movies"})
public class MovieController extends ModelController<Movie> {

    private final MovieDTO movieDTO;

    @Autowired
    public MovieController(MovieDTO movieDTO) {
        this.movieDTO = movieDTO;
    }

    @Override
    protected ResponseEntity<List<Movie>> reads() {
        return ResponseEntity.ok(movieDTO.findAll());
    }

    @Override
    protected ResponseEntity<Set<Movie>> readsRelations() {
        return null;
    }

    @Override
    protected ResponseEntity<List<Object>> readsSpecifyColumn(@PathVariable("columnName") String columnName) {
        return ResponseEntity.ok(movieDTO.findAllOnlyColumn(columnName));
    }

    @Override
    protected ResponseEntity<Movie> readByPrimaryKey(@PathVariable("pk") String pk) {
        return ResponseEntity.ok(movieDTO.findOneByPk(pk));
    }

    /**
    @GetMapping(value = "/read-relation/{pk}")
    protected ResponseEntity<Movie> readRelationByPrimaryKey(@PathVariable("pk") String pk) {
        return ResponseEntity.ok(movieDTO.findOneRelationByPk(pk));
    }
    */

    @Override
    protected ResponseEntity<Integer> create(@RequestBody Movie model) {
        return ResponseEntity.ok(movieDTO.saveModel(model));
    }

    @Override
    protected ResponseEntity<Integer> updateByPrimaryKey(@RequestBody Movie model, @RequestParam("pk") String pk) {
        return ResponseEntity.ok(movieDTO.updateModelByPk(model,pk));
    }

    @Override
    protected ResponseEntity<Integer> deleteByPrimaryKey(@RequestParam("pk") String pk) {
        return ResponseEntity.ok(movieDTO.deleteModelByPk(pk));
    }
    
}
