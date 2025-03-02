package academy.devdojo.spring_boot_essentials.controller;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;
import academy.devdojo.spring_boot_essentials.service.AnimeService;
import academy.devdojo.spring_boot_essentials.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // Declares the class as controller, rest, and which returns a string
@RequestMapping("animes") // Map as localhost:8080/animes, usually plural
@Log4j2
@AllArgsConstructor // Creates a constructor with all args contained (dependency injection)
public class AnimeController { // A simple class, which only contains the endpoints

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping("/{id}") // Path variables
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return ResponseEntity.ok(animeService.findByIdOrThrowBadResquestException(id));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody animePostRequestBody){ // To avoid a case of Anime + ID, because this Post needs only the name of Anime
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED); // Returns 201 instead 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}