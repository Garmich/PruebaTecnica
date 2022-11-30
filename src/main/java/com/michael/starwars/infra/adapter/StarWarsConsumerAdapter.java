package com.michael.starwars.infra.adapter;

import com.michael.starwars.domain.model.Film;
import com.michael.starwars.domain.model.FilmResponse;
import com.michael.starwars.domain.model.PeopleResponse;
import com.michael.starwars.domain.model.StarShipResponse;

public interface StarWarsConsumerAdapter {
    FilmResponse filmFirtsPage();
    PeopleResponse peopleFirtsPage();

    StarShipResponse starShipFirtsPage();

    Film request();
}
