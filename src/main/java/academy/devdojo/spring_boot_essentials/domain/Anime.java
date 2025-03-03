package academy.devdojo.spring_boot_essentials.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data // Generate getter, setter, toString, equals, hashCode...
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder // To build automatically the object if is informed only the name (random ID)
public class Anime { // Needs a Getter and Setter to be instantiated by Jackson
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
