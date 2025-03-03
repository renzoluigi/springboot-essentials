package academy.devdojo.spring_boot_essentials.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails { // The pattern of details that will appear if throws BadRequestException

}
