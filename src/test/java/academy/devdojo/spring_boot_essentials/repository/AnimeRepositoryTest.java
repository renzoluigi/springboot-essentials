package academy.devdojo.spring_boot_essentials.repository;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest // To create tests
@DisplayName("Tests for Anime Repository") // To set the name of test
@Log4j2
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when successful")
    void save_PersistsAnime_WhenSuccessful(){ // method + what should he do + when should he do
        Anime animeToBeSaved = createAnime();

        Anime savedAnime = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_UpdatesAnime_WhenSuccessful(){ // method + what should he do + when should he do

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Fairy Tail");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){ // method + what should he do + when should he do

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id find anime by ID when successful")
    void findById_FindAnimeById_WhenSuccessful(){ // method + what should he do + when should he do

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Optional<Anime> animeOptional = animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isNotEmpty();
    }

    @Test
    @DisplayName("Find By Name find anime by name and returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){ // method + what should he do + when should he do
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        List<Anime> listOfAnime = animeRepository.findByName(animeSaved.getName());

        Assertions.assertThat(listOfAnime).isNotEmpty().contains(animeSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){ // method + what should he do + when should he do
        List<Anime> animes = this.animeRepository.findByName("xaxa");

        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){ // method + what should he do + when should he do
        Anime anime = new Anime();
//        Assertions.assertThatThrownBy(()-> this.animeRepository.save(anime)).isInstanceOf(ConstraintViolationException.class);
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> this.animeRepository.save(anime)).withMessageContaining("Anime name cannot be empty");
    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Yuyu Hakusho")
                .build();
    }
}