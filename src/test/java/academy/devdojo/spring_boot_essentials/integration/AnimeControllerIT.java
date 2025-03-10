package academy.devdojo.spring_boot_essentials.integration;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.repository.AnimeRepository;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.spring_boot_essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createAnimeToBeSaved;
import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createValidAnime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // To don't have conflicts on ports of test and running (8080)
@AutoConfigureTestDatabase // To realize tests with database in memory
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // When executes all tests at the same time, the @AutoConfigureTestDatabase use the same database, with this annotation, the database will be recreated before each test method
public class AnimeControllerIT { // Integration test (initialize server completely, it's like as a unity test, but running all as a deploy in production)
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @LocalServerPort // To identify the port used here
    private int port;

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
        }).getBody(); // PageableResponse extends PageImpl

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, 1);

        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        String url = "/animes/find?name=" + savedAnime.getName();
        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        String expectedName = createValidAnime().getName();
        String url = "/animes/find?name=" + expectedName;
        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class, "anime");

        Assertions.assertThat(animeResponseEntity).isNotNull(); // Assertion for entity
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull(); // Assertions for object
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull().isEqualTo(1);
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());
        savedAnime.setName("Attack on Titan");

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
