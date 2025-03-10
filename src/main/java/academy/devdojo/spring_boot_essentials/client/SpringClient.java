package academy.devdojo.spring_boot_essentials.client;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) { // The rest template is useful request for validate the information based in a pattern, it's a good way to pass data from other's APIs to your API

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 45);
        log.info(entity);

        Anime animeObject = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 45); // Can be used if have several placeholders
        log.info(animeObject);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class); // To get an array of all objects from url
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});// To get animes list
        log.info(exchange.getBody());

        Anime rezero = Anime.builder().name("Re:Zero").build();
        Anime rezeroSaved = new RestTemplate().postForObject("http://localhost:8080/animes", rezero, Anime.class); // Creating a Post request
        log.info("Saved anime {} ", rezeroSaved);

        Anime slime = Anime.builder().name("Slime").build();
        ResponseEntity<Anime> slimeSaved = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(slime, createJsonHeader()), Anime.class); // Creating Post request as Entity
        log.info("Saved anime {} ", slimeSaved);

        Anime animeToBeUpdated = slimeSaved.getBody();
        animeToBeUpdated.setName("Slime 2");

        ResponseEntity<Void> slimeUpdated = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.PUT, new HttpEntity<>(animeToBeUpdated, createJsonHeader()), Void.class);
        log.info(slimeUpdated);

        ResponseEntity<Void> slimeUpdatedDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class, animeToBeUpdated.getId());
        log.info(slimeUpdatedDelete);

    }
    public static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
