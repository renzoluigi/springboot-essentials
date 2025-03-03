package academy.devdojo.spring_boot_essentials.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // To replace new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
