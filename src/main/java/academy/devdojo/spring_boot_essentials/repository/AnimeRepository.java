package academy.devdojo.spring_boot_essentials.repository;

import academy.devdojo.spring_boot_essentials.domain.Anime;

import java.util.List;

public interface AnimeRepository { // The queries of DB
    List<Anime> listAll();
}
