package com.michael.starwars.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class StarShipResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private String next;
    private String previous;
    private List<StarShip> results;
}
