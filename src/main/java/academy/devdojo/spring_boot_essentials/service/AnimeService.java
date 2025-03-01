package academy.devdojo.spring_boot_essentials.service;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service //To be a Spring Bean
public class AnimeService { // The business logic will be here, so the controller is cleaner. In that case, it simules access to a DB
    private static List<Anime> animes;

    static {
        animes = new ArrayList<>(List.of(new Anime(1L,"Dragon Ball Z"), new Anime(2L,"Berserk"), new Anime(3L, "One Piece")));
    }
    // private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return animes;
    }

    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found")); // If not found the id, throw the status exception
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3,100000));
        animes.add(anime);
        return anime;
    }

    public void delete(long id) {
        animes.remove(findById(id));
    }
}
