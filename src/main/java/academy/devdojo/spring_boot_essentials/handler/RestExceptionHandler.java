package academy.devdojo.spring_boot_essentials.handler;

import academy.devdojo.spring_boot_essentials.exception.BadRequestException;
import academy.devdojo.spring_boot_essentials.exception.BadRequestExceptionDetails;
import academy.devdojo.spring_boot_essentials.exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice // To call to all controllers must utilize the info of the class based on flag "ExceptionHandler"
public class RestExceptionHandler { // This function treats the BadRequestException for more friendly throw exception

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails
                        .builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value()) // or 400
                        .title("Bad Request Exception, check the documentation") // Title of the exception
                        .details(badRequestException.getMessage()) // The message passed in param
                        .developerMessage(badRequestException.getClass().getName()) // Localization of the exception
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", ")); // Collect the fields from list fieldErrors of the exception
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", ")); // Collect the messages from each field from fieldErrors
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value()) // or 400
                        .title("Bad Request Exception, invalid fields") // Title of the exception
                        .details("Check the field(s) error") // The message passed in param
                        .developerMessage(methodArgumentNotValidException.getClass().getName()) // Localization of the exception
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
