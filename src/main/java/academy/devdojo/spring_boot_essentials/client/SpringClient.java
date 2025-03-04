package academy.devdojo.spring_boot_essentials.client;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) { // The rest template is useful to validate the information based in a pattern
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 45);
        log.info(entity);

        Anime animeObject1 = new RestTemplate().getForObject("http://localhost:8080/animes/45", Anime.class);
        log.info(animeObject1);

        Anime animeObject2 = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 45); // Can be used if have several placeholders
        log.info(animeObject2);
    }
}
