package com.michael.starwars.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class StarShipPage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private String next;
    private String previous;
    private List<StarShip> results;
}
