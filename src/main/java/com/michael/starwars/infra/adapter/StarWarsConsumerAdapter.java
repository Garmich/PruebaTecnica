package com.michael.starwars.infra.adapter;

import com.michael.starwars.domain.model.FilmPage;
import com.michael.starwars.domain.model.PeoplePage;
import com.michael.starwars.domain.model.StarShipPage;

public interface StarWarsConsumerAdapter {

    FilmPage filmPage(String url);
    PeoplePage peoplePage(String url);
    StarShipPage starShipPage(String url);
}
