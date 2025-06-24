package com.ttknp.manytomanyrestapicontroller.dto;

import com.ttknp.manytomanyrestapicontroller.entities.Movie;
import com.ttknp.manytomanyrestapicontroller.entities.Movie;
import com.ttknp.manytomanyrestapicontroller.services.ModelJdbcExecute;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieDTO extends ModelJdbcExecute<Movie> {

    @Override
    public List<Movie> findAll() {
        return null;
    }

    @Override
    public Set<Movie> findAllRelationsModels() {
        return null;
    }

    @Override
    public <U> List<U> findAllOnlyColumn(String columnName) {
        return null;
    }

    @Override
    public <U> Movie findOneByPk(U pk) {
        return null;
    }

    @Override
    public Integer saveModel(Movie model) {
        try {
            return jdbcUpdateExecuteHelper.insertOne(Movie.class,model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Integer updateModelByPk(Movie model, U pk) {
        return 0;
    }

    @Override
    public <U> Integer deleteModelByPk(U pk) {
        return 0;
    }
}
