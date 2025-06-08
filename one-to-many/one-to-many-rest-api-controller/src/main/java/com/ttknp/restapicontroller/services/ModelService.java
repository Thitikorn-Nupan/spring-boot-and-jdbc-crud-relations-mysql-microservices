package com.ttknp.restapicontroller.services;

import java.util.List;
import java.util.Set;

public interface ModelService <T>{

    // Reads
    List<T> findAll();
    Set<T> findAllRelationsModels();
    <U> List<U> findAllOnlyColumn(String columnName);
    // Read
    T findOneBy(Object... params);
    <U> T findOneByPk(U pk);
    <U> T findOneRelationsModelsByPk(U pk);
    Integer countAllRows();
    // Create
    Integer saveModel(T model);
    <U1,U2> Integer saveRelationsModels(U1 pk1, U2 pk2);
    // Update
    <U> Integer updateModelByPk(T model, U pk);
    // Delete
    <U> Integer deleteModelByPk( U pk);

}
