package com.michael.starwars.infra.adapter;

import com.michael.starwars.domain.model.Film;
import com.michael.starwars.domain.model.FilmResponse;
import com.michael.starwars.domain.model.PeopleResponse;
import com.michael.starwars.domain.model.StarShipResponse;
import com.michael.starwars.infra.utils.StarWarsUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StarWarsConsumerService implements StarWarsConsumerAdapter {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private WebClient webClient = WebClient.create(StarWarsUrl.Films);

    @Override
    public FilmResponse filmFirtsPage() {
        Mono<FilmResponse> filmResponseMono = WebClient
                .create(StarWarsUrl.Films)
                .get()
                .retrieve()
                .bodyToMono(FilmResponse.class);

        FilmResponse film = filmResponseMono
                .share().block();
        return film;
    }

    @Override
    public PeopleResponse peopleFirtsPage() {
        Mono<PeopleResponse> pageResponseMono = WebClient
                .create(StarWarsUrl.People)
                .get()
                .retrieve()
                .bodyToMono(PeopleResponse.class);

        PeopleResponse page = pageResponseMono
                .share().block();
        return page;
    }

    @Override
    public StarShipResponse starShipFirtsPage() {
        Mono<StarShipResponse> pageResponseMono = WebClient
                .create(StarWarsUrl.StarShips)
                .get()
                .retrieve()
                .bodyToMono(StarShipResponse.class);

        StarShipResponse page = pageResponseMono
                .share().block();
        return page;
    }

    @Override
    public Film request() {
        Mono<Film> filmMono = WebClient
                .create(StarWarsUrl.Films+ "1/")
                .get()
                .retrieve()
                .bodyToMono(Film.class);

        Film film = filmMono
                .share().block();
        return film;
    }
}
