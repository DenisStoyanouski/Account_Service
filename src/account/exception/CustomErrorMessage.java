package account.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonPropertyOrder({"timestamp", "status", "error", "message", "path"})
@Data
@Getter
@Builder
public class CustomErrorMessage {
    private LocalDateTime timestamp;
    @JsonProperty(value = "status")
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
