package com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_many;

public class SQLOneToMany {
    // Employees
    public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees";
    public static final String SELECT_ONE_EMPLOYEES_BY_PK = "SELECT * FROM employees WHERE eid = ?";
    public static final String SELECT_COUNT_EMPLOYEES = "SELECT count(*) FROM employees";
    public static String SELECT_ALL_SPECIFY_COLUMN_EMPLOYEES = "SELECT %s FROM employees";
    public static final String INSERT_ONE_EMPLOYEES = "INSERT INTO employees (eid, firstname, lastname, position, active, salary) VALUES (?, ?, ?,?,?, ?);";
    public static final String DELETE_ONE_EMPLOYEES = "DELETE FROM employees WHERE eid = ?";

    public static final String UPDATE_ONE_EMPLOYEES = "UPDATE employees SET firstname = ? , lastname = ? , position = ? , active = ? , salary = ? WHERE eid = ?;";


    // Addresses
    public static final String SELECT_ALL_ADDRESSES = "SELECT * FROM addresses";
    public static String SELECT_ALL_SPECIFY_COLUMN_ADDRESSES = "SELECT %s FROM addresses";
    public static final String SELECT_ONE_ADDRESSES_BY_PK = "SELECT * FROM addresses WHERE aid = ?";
    public static final String SELECT_COUNT_ADDRESSES = "SELECT count(*) FROM employees";




    // Employees & Addresses
    public static final String INSERT_ONE_RELATIONS = "INSERT INTO employees_addresses (eid, aid) VALUES (?, ?);";
    public static final String SELECT_COUNT_RELATIONS_BY_EMPLOYEES_PK = "SELECT COUNT(*) FROM employees_addresses WHERE eid = ?;";
    public static final String DELETE_RELATIONS_BY_EMPLOYEES_PK = "DELETE FROM employees_addresses WHERE eid = ?;";
    public static final String SELECT_ONE_EMPLOYEES_ADDRESSES_BY_EMPLOYEES_PK = "\tSELECT * FROM employees e\n" +
            "\tJOIN employees_addresses e_a\n" +
            "\tON e_a.eid = e.eid\n" +
            "\tJOIN addresses a \n" +
            "\tON e_a.aid = a.aid\n" +
            "\tWHERE e.eid = ?";
    public static final String SELECT_ALL_EMPLOYEES_ADDRESSES = "\tSELECT e.*, a.* FROM employees e\n" +
            "\tJOIN employees_addresses e_a\n" +
            "\tON e_a.eid = e.eid\n" +
            "\tJOIN addresses a \n" +
            "\tON e_a.aid = a.aid\n"+
            "\tORDER BY e_a.eid asc";


}
