package academy.devdojo.spring_boot_essentials.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
    @NotBlank(message = "Anime name cannot be empty") // Treats null, blank and empty
    private String name;
}
