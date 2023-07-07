package account.presentation;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Data
@Getter
@Builder
public class CustomErrorMessage {
    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;

    public CustomErrorMessage(
            LocalDateTime timestamp,
            int statusCode,
            String error,
            String message,
            String path) {

        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.path = path;
    }
}
