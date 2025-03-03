package academy.devdojo.spring_boot_essentials.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails { // The ExceptionDetails with the additional fields (specialization)
    private final String fields; // Invalid fields
    private final String fieldsMessage;
}
