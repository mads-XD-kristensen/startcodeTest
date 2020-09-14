package DTO;

import entities.Movie;
import java.util.StringJoiner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madsa
 */
public class MovieDTO {
    
    private long id;
    private int year;
    private String title;
    private String actors;

    public MovieDTO(Movie m) {
        this.id = m.getId();
        this.year = m.getYear();
        this.title = m.getTitle();
        StringJoiner joiner = new StringJoiner(", ");
        for(int i = 0; i < m.getActors().length; i++) {
         joiner.add(m.getActors()[i]);
      }
      this.actors = joiner.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
    
}
