package com.michael.starwars.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Film {

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private LocalDate release_date;
//    private List<String> characters;
//    private List<String> planets;
//    private List<String> starships;
//    private List<String> vehicles;
//    private List<String> species;
    private LocalDate created;
    private LocalDate edited;
    private String url;
}
