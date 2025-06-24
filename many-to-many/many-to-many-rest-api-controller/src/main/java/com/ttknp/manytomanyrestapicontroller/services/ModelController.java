package com.ttknp.manytomanyrestapicontroller.services;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Set;

public abstract class ModelController <T> {
    // this can access by /<prefix main extends>/server
    @GetMapping(value = "/server")
    public ResponseEntity<String> testServer() {
        return ResponseEntity.ok("Hello World");
    }
    @GetMapping(value = "/reads")
    protected abstract ResponseEntity<List<T>> reads();
    @GetMapping(value = "/reads-relations")
    protected abstract ResponseEntity<Set<T>> readsRelations();
    @GetMapping(value = "/reads/{columnName}") // in jdk 17 have to specify path var name
    protected abstract ResponseEntity<List<Object>> readsSpecifyColumn(String columnName);
    @GetMapping(value = "/read/{pk}") // in jdk 17 have to specify path var name
    protected abstract ResponseEntity<T> readByPrimaryKey(String pk);
    @PostMapping(value = "/create")
    protected abstract ResponseEntity<Integer> create(T model);
    @PutMapping(value = "/update")
    protected abstract ResponseEntity<Integer> updateByPrimaryKey(T model, String pk);
    @DeleteMapping(value = "/delete")
    protected abstract ResponseEntity<Integer> deleteByPrimaryKey(String pk);
}
