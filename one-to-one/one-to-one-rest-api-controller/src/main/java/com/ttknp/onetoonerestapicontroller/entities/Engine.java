package com.ttknp.onetoonerestapicontroller.entities;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Way to get table name by Java POJO in JDBC
 * First use annotation @Table and specify table name
 */
@Table(name = "engines") //
public class Engine {

    private String eid;
    private String code;

    // Default constructor
    public Engine() {
    }

    // Parameterized constructor
    public Engine(String eid, String code) {
        this.eid = eid;
        this.code = code;
    }

    // Getter methods
    public String getEid() {
        return eid;
    }

    public String getCode() {
        return code;
    }

    // Setter methods
    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Engine{");
        sb.append("eid='").append(eid).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
