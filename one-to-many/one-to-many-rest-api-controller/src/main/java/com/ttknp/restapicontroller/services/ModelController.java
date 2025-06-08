package com.ttknp.restapicontroller.services;

/*import com.ttknp.restapicontroller.entities.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

public abstract class  ModelController <T> {

    // this can access by /<prefix main extends>/server
    @GetMapping(value = "/server")
    public ResponseEntity<String> testServer() {
        return ResponseEntity.ok("Hello World");
    }

    abstract public ResponseEntity<List<T>> reads();
    abstract public ResponseEntity<Set<T>> readsRelations();
    abstract public ResponseEntity<List<Object>> readsSpecifyColumn(String columnName); // can add @PathVariable("columnName")  later
    abstract public ResponseEntity<Integer> readsCount();
    abstract public ResponseEntity<T> readByPrimaryKey(String pk);
    abstract public ResponseEntity<T> readRelationsByPrimaryKey(String pk);
    abstract public ResponseEntity<Integer> create(T model);
    abstract public ResponseEntity<Integer> createRelation(String eid ,String aid);
    abstract public ResponseEntity<Integer> updateByPrimaryKey(T model, String pk);
    abstract public ResponseEntity<Integer> deleteByPrimaryKey(String pk);
}
