package academy.devdojo.spring_boot_essentials.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private Long id;
    @NotBlank(message = "Anime name cannot be empty")
    private String name;
}
