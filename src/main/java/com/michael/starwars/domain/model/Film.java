package com.michael.starwars.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    @JsonProperty("episode_id")
    private int episodeId;
    @JsonProperty("opening_crawl")
    private String openingCrawl;
    private String director;
    private String producer;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String created;
    private String edited;
    private String url;
}
