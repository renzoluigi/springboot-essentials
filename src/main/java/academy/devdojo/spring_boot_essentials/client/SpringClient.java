package academy.devdojo.spring_boot_essentials.client;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) { // The rest template is useful request for validate the information based in a pattern

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 45);
        log.info(entity);

        Anime animeObject = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 45); // Can be used if have several placeholders
        log.info(animeObject);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class); // To get an array of all objects from url
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {});// To get an animes list
        log.info(exchange.getBody());


    }
}
