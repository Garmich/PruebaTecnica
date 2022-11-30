package com.michael.starwars.domain.services;

import com.michael.starwars.domain.model.Film;
import com.michael.starwars.infra.adapter.StarWarsConsumerAdapter;
import com.michael.starwars.infra.port.input.StarWarsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StarWarsService implements StarWarsUseCase {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StarWarsConsumerAdapter starWarsAdapter;

    public void saveFilms (List<Film> films) {

    }

}