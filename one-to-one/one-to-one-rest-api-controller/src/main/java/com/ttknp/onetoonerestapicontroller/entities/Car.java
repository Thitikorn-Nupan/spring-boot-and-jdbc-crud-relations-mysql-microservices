package com.ttknp.onetoonerestapicontroller.entities;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Way to get table name by Java POJO in JDBC
 * First use annotation @Table and specify table name
*/
@Table(name = "cars") //
public class Car {
    private String cid;
    private String brand;
    private String model;
    private BigDecimal price; // Using BigDecimal for DECIMAL(8,2)
    private LocalDate releaseDate; // on json can be string as "2024-12-30"

    private Engine engine;

    public Car(String cid, String brand, String model, BigDecimal price, LocalDate releaseDate) {
        this.cid = cid;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    public Car() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Car setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Car{");
        sb.append("cid='").append(cid).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", price=").append(price);
        sb.append(", releaseDate=").append(releaseDate);
        sb.append('}');
        return sb.toString();
    }
}
