package com.ttknp.jdbcservice.helpers.named_param_jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class NamedParamJdbcHelper {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public NamedParamJdbcHelper(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

}
