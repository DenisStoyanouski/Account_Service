package account.presentation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleUsernameNotFound(
            UsernameNotFoundException e, WebRequest request) {

        var body = CustomErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .message(e.getLocalizedMessage())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameIsOccupiedException.class)
    public ResponseEntity<CustomErrorMessage> handleUsernameIsOccupied(
            UsernameIsOccupiedException e,
            WebRequest request) {

        var body = CustomErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CustomErrorMessage> handleAuthentication(
            AuthenticationException e,
            HttpServletRequest request) {

        var body = CustomErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message("")
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .path(request.getServletPath())
                .build();
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, headers, status);
    }


}
