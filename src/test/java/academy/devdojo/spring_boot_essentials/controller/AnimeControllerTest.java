package academy.devdojo.spring_boot_essentials.controller;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;
import academy.devdojo.spring_boot_essentials.service.AnimeService;
import academy.devdojo.spring_boot_essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.spring_boot_essentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createValidAnime;

@ExtendWith(SpringExtension.class) // Don't need to start the application to test as @SpringBootTest
class AnimeControllerTest { // This class has a conflict with DateUtil

    @InjectMocks // Use when you want to test the class
    private AnimeController animeController;

    @Mock // For all classes in the class (AnimeController: AnimeService) to don't need to restart several times
    private AnimeService animeServiceMock;

    @BeforeEach // Before each test in this class, execute this test
    public void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(createValidAnime()));

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())) // When animeServiceMock executes list all with any argument, will return animePage (a page with list of anime) instead animeRepository.findAll(pageable)
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class)); // Because returns void

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = createValidAnime().getName();
        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes.get(0).getName()).contains(expectedName);
        Assertions.assertThat(animes).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long id = createValidAnime().getId();
        Anime animes = animeController.findById(1).getBody();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("findByName returns anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        String expectedName = createValidAnime().getName();
        List<Anime> animes = animeController.findByName("any name").getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("any anime").getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime expectedAnime = createValidAnime();
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        //or

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeController.delete(createValidAnime().getId()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(createValidAnime().getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}