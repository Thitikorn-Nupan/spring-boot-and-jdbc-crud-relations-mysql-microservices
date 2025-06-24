package com.ttknp.manytomanyrestapicontroller.entities;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
@Table(name = "movies")
public class Movie {
    private String mid;
    private String title;
    private String categories;
    private BigDecimal rate;
    private LocalDate year;

    public Movie() {
    }

    public Movie(String mid, String title, String categories, BigDecimal rate, LocalDate year) {
        this.mid = mid;
        this.title = title;
        this.categories = categories;
        this.rate = rate;
        this.year = year;
    }

    // Getters
    public String getMid() {
        return mid;
    }

    public String getTitle() {
        return title;
    }

    public String getCategories() {
        return categories;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDate getYear() {
        return year;
    }

    // Setters
    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return "Movie{" +
               "mid='" + mid + '\'' +
               ", title='" + title + '\'' +
               ", categories='" + categories + '\'' +
               ", rate=" + rate +
               ", year=" + year +
               '}';
    }
}