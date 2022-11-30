package com.michael.starwars.aplication.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "starwars")
public class StarWarsController {

/*    @Autowired
    private StarWarsService starWarsService;

    @GetMapping(value = "/planets/{id}")
    Planet findById(@PathVariable String id) throws NoSuchMovieInException {
        return this.starWarsService.getSwapiPlanetById(id);
    }

    @GetMapping(path = "/movies")
    MovieSearch findByTypeAndName(@RequestParam(name = "type", required = true) String type,
                                  @RequestParam(name = "name", required = true) String name) throws NoSuchMovieInException {
        return this.starWarsService.findByTypeAndName(type, name);
    }

    @GetMapping(value = {"/allplanets/{name}"})
    ResponseEntity<RestResponse<PlanetWrapper>> getAllPlanets(@PathVariable String name) throws RestClientException, NoSuchMovieInException {
        RestResponse<PlanetWrapper> response = null;

        if(Optional.ofNullable(this.starWarsService.getSwapiAllPlanet(name)).isPresent()) {
            response = new RestResponse<>(this.starWarsService.getSwapiAllPlanet(name),
                    null, HttpStatus.OK);
            return ResponseEntity.ok(response);
        } else {
            response = new RestResponse<>(null,
                    "No Data available", HttpStatus.NO_CONTENT);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

    }*/
}
