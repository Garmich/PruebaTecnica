package com.michael.starwars.domain.services;

import com.michael.starwars.domain.model.Film;
import com.michael.starwars.domain.model.FilmPage;
import com.michael.starwars.domain.model.PeoplePage;
import com.michael.starwars.infra.adapter.StarWarsConsumerAdapter;
import com.michael.starwars.infra.port.input.StarWarsUseCase;
import com.michael.starwars.infra.port.output.JdbcPort;
import com.michael.starwars.infra.utils.StarWarsUrl;
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
    @Autowired
    private JdbcPort jdbcPort;

    public void importFilms (){
        String next = StarWarsUrl.Films;
        do {
            FilmPage page = starWarsAdapter.filmPage(next);
            List<Film> result = (List<Film>) page.getResults();
            for(Film film : result) {
//                System.out.println("film = " + film);
//                film.setTitle("HOLA PUTA");
                jdbcPort.save(film);
            }
            next = page.getNext();
        } while (next != null);
    }
    public void importPeople (){
        String next = StarWarsUrl.People;
//        do {
//            PeoplePage page = starWarsAdapter.peoplePage(next);
//            page.getResults().forEach(jdbcPort::save);
//            next = page.getNext();
//        } while (next != null);
    }

    public void importStarShip (){
        String next = StarWarsUrl.StarShips;
//        do {
//            PeoplePage page = starWarsAdapter.peoplePage(next);
//            page.getResults().forEach(jdbcPort::save);
//            next = page.getNext();
//        } while (next != null);
    }

}