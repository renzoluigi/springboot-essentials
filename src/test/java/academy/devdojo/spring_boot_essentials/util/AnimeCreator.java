package academy.devdojo.spring_boot_essentials.util;

import academy.devdojo.spring_boot_essentials.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder().name("Akame ga Kill!").build();
    }

    public static Anime createValidAnime() {
        return Anime.builder().name("Akame ga Kill!").id(33L).build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder().name("Parasyte").id(33L).build();
    }

}
