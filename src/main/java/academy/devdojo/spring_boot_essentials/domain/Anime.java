package academy.devdojo.spring_boot_essentials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data // Generate getter, setter, toString, equals, hashCode...
@AllArgsConstructor
public class Anime { // Needs a Getter and Setter to be instantiated by Jackson
    private Long id;
    private String name;
}
