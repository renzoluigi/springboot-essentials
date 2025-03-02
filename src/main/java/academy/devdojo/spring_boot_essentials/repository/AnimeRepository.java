package academy.devdojo.spring_boot_essentials.repository;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> { // The queries of DB

    public List<Anime> findByName(String name);
}
