package account.Payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

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
    @NotBlank(message = "The period mustn't be null, empty or blank")
    @Pattern(regexp = "^([1-9]|1[0-2])-\\d\\d\\d\\d$", message = "Wrong data format")
    private String period;
    @NotBlank(message = "The salary mustn't be null, empty or blank")
    @Positive
    private long salary;

}
