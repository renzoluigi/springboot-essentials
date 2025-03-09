package academy.devdojo.spring_boot_essentials.util;

import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;

import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createAnimeToBeSaved;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody.builder().name(createAnimeToBeSaved().getName()).build();
    }
}
