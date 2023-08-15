package criteo.sponsoredads.Exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class AllExceptionsHandler extends ResponseEntityExceptionHandler {
    public record ErrorResponseDetails(String message,
                                       @JsonSerialize(using = LocalDateTimeSerializer.class)
                                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                                       LocalDateTime dateTime,
                                       String details) { }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ErrorResponseDetails error = new ErrorResponseDetails("Internal server failure. See logs.",
                LocalDateTime.now(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleAllExceptions(IllegalArgumentException ex, WebRequest request) {
        log.error(ex.getMessage());
        ErrorResponseDetails error = new ErrorResponseDetails(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public final ResponseEntity<Object> handleAllExceptions(InvalidParameterException ex, WebRequest request) {
        log.error(ex.getMessage());
        ErrorResponseDetails error = new ErrorResponseDetails(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
