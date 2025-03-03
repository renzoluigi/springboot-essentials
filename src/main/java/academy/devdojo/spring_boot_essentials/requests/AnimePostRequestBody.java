package academy.devdojo.spring_boot_essentials.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AnimePostRequestBody {
    @NotBlank(message = "Anime name cannot be empty") // Treats null, blank and empty
    private String name;
}
