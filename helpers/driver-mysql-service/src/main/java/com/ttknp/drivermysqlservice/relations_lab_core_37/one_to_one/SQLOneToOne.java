package com.ttknp.drivermysqlservice.relations_lab_core_37.one_to_one;

public class SQLOneToOne {

    // Cars
    public static final String SELECT_ALL_CARS = "SELECT * FROM cars";
    public static String SELECT_ALL_SPECIFY_COLUMN_CARS = "SELECT %s FROM cars";
    public static final String UPDATE_ONE_CARS = "UPDATE cars SET brand = ? , model = ? , price = ? , release_date = ? WHERE cid = ?;";
    public static final String DELETE_ONE_CARS = "DELETE FROM cars WHERE cid = ?";


    // Engines
    public static String SELECT_ALL_SPECIFY_COLUMN_ENGINES = "SELECT %s FROM engines";


    public static final String SELECT_COUNT_RELATIONS_BY_CARS_PK = "SELECT COUNT(*) FROM cars_engines WHERE cid = ?;";
    public static final String DELETE_RELATIONS_BY_CARS_PK = "DELETE FROM cars_engines WHERE cid = ?;";
    public static final String SELECT_ALL_CARS_ENGINES = "\tSELECT c.*, e.* FROM cars c\n" +
            "\tJOIN cars_engines c_e\n" +
            "\tON c_e.cid = c.cid\n" +
            "\tJOIN engines e \n" +
            "\tON c_e.eid = e.eid\n"+
            "\tORDER BY c_e.eid asc";

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



}
