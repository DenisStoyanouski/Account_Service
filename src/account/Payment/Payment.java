package account.Payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "payment")
public class Payment {
    @Id
    @SequenceGenerator(
            name = "payment_seq",
            sequenceName = "payment_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_seq"
    )
    private long id;
    @NotBlank(message = "The employee mustn't be null, empty or blank")
    private String employee;
    @JsonDeserialize(using = CustomYearMonthDeserializer.class)
    @Column(
            name = "period",
            columnDefinition = "date"
    )
    @Convert(
            converter = YearMonthDateAttributeConverter.class
    )
    private YearMonth period;

    @NotNull(message = "The salary mustn't be null, empty or blank")
    @Positive(message = "The salary must be positive")
    private long salary;
}
