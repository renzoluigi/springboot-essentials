package academy.devdojo.spring_boot_essentials.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generate getter, setter, toString, equals, hashCode...
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Anime { // Needs a Getter and Setter to be instantiated by Jackson
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
