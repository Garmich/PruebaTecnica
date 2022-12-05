package com.michael.starwars.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class FilmPage {

    private int count;
    private String next;
    private String previous;
    private List<Film> results;
}
