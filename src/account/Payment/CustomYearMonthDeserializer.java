package account.Payment;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

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
        return YearMonth.parse(date, formatter);
    }
}
