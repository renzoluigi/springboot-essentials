package academy.devdojo.spring_boot_essentials.mapper;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Dependency injection
public interface AnimeMapper { // Needs to be abstract
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class); // To instance the class

    Anime toAnime(AnimePostRequestBody animePostRequestBody);

    Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
