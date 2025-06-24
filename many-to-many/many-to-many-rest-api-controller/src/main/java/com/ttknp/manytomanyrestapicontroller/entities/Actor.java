package com.ttknp.manytomanyrestapicontroller.entities;

import com.ttknp.jdbcservice.helpers.annotation.IgnoreGenerateSQL;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Set;

@Table(name = "actors")
public class Actor {

    private String aid;
    private String fullname;
    private LocalDate born; // LocalDate map Date
    private String contact;
    @IgnoreGenerateSQL
    private Set<Movie> movies;

    public Actor() {
    }

    public Actor(String aid, String fullname, LocalDate born, String contact) {
        this.aid = aid;
        this.fullname = fullname;
        this.born = born;
        this.contact = contact;
    }

    // Getters
    public String getAid() {
        return aid;
    }

    public String getFullname() {
        return fullname;
    }

    public LocalDate getBorn() {
        return born;
    }

    public String getContact() {
        return contact;
    }

    // Setters
    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" +
               "aid='" + aid + '\'' +
               ", fullname='" + fullname + '\'' +
               ", born=" + born +
               ", contact='" + contact + '\'' +
               '}';
    }
}