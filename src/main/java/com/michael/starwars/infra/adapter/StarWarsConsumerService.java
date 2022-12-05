package com.michael.starwars.infra.adapter;

import com.michael.starwars.domain.model.FilmPage;
import com.michael.starwars.domain.model.PeoplePage;
import com.michael.starwars.domain.model.StarShipPage;
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

    public FilmPage filmPage(String url) {
        Mono<FilmPage> filmResponseMono = WebClient
                .create(url)
                .get()
                .retrieve()
                .bodyToMono(FilmPage.class);

        return filmResponseMono
                .share().block();
    }

    @Override
    public PeoplePage peoplePage(String url) {
        Mono<PeoplePage> pageResponseMono = WebClient
                .create(url)
                .get()
                .retrieve()
                .bodyToMono(PeoplePage.class);

        return pageResponseMono
                .share().block();
    }

    @Override
    public StarShipPage starShipPage(String url) {
        Mono<StarShipPage> pageResponseMono = WebClient
                .create(url)
                .get()
                .retrieve()
                .bodyToMono(StarShipPage.class);

        return pageResponseMono
                .share().block();
    }
}
