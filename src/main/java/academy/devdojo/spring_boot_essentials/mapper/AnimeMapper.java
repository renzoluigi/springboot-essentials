package academy.devdojo.spring_boot_essentials.mapper;

import academy.devdojo.spring_boot_essentials.domain.Anime;
import academy.devdojo.spring_boot_essentials.requests.AnimePostRequestBody;
import academy.devdojo.spring_boot_essentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Dependency injection
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class); // To instance the class

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
