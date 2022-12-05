package com.michael.starwars.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class StarWarsPage {
    private static final long serialVersionUID = 1L;

    private int count;
    private String next;
    private String previous;
    private List<Object> results;
}
