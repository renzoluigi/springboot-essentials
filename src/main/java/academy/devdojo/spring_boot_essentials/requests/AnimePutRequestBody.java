package academy.devdojo.spring_boot_essentials.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnimePutRequestBody {
    private Long id;
    @NotBlank(message = "Anime name cannot be empty")
    private String name;
}
