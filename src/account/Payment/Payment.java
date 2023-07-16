package account.Payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;
import java.util.Date;

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
    /*@NotBlank(message = "The period mustn't be null, empty or blank")
    @Pattern(regexp = "^(0[1-9]|1[0-2])-\\d\\d\\d\\d$", message = "Wrong data format")
    private String period;*/
    @Pattern(regexp = "^(0[1-9]|1[0-2])-\\d\\d\\d\\d$", message = "Wrong data format")
    @DateTimeFormat(pattern = "MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date period;
    @NotNull(message = "The salary mustn't be null, empty or blank")
    @Positive(message = "The salary must be positive")
    private long salary;
}
