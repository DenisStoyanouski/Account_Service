package account.Payment;

import account.exception.PeriodIsNotValidException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomYearMonthDeserializer extends StdDeserializer<YearMonth> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

    public CustomYearMonthDeserializer() {
        this(null);
    }

    protected CustomYearMonthDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public YearMonth deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String date = p.getText();
        try {
            return YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new PeriodIsNotValidException("Wrong period format");
        }
    }
}
