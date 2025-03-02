package academy.devdojo.spring_boot_essentials.service;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.repository.AnimeRepository;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service //To be a Spring Bean
@RequiredArgsConstructor
public class AnimeService { // The business logic will be here, so the controller is cleaner.
    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadResquestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build()); //returns the name + ID. can be used too: animeRepository.save(new Anime(ThreadLocalRandom.current().nextLong(4, 10000), animePostRequestBody.getName()));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadResquestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadResquestException(animePutRequestBody.getId());
        Anime anime = Anime.builder()
                .name(animePutRequestBody.getName())
                .id(savedAnime.getId()) // Make sure the anime is on the DB
                .build();
        animeRepository.save(anime);
    }
}
