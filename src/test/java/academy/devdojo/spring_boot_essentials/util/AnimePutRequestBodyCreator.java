package academy.devdojo.spring_boot_essentials.util;

import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;

import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createValidAnime;
import static academy.devdojo.spring_boot_essentials.util.AnimeCreator.createValidUpdatedAnime;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.builder().name(createValidUpdatedAnime().getName()).id(createValidUpdatedAnime().getId()).build();
    }
}
