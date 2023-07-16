package account.business;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "_user")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq"
    )
    private Long id;
    @NotBlank(message = "The name mustn't be null, empty or blank")
    @Column
    private String name;
    @NotBlank(message = "The lastname mustn't be null, empty or blank")
    @Column
    private String lastname;
    @NotBlank(message = "The lastname mustn't be null, empty or blank")
    @Pattern(regexp = "\\w+(@acme.com)$", message = "The email domain must be acme.com")
    private String email;
    @NotBlank(message = "The password mustn't be null, empty or blank")
    /*@Size(min = 12, message = "The password length must be at least 12 chars!")
    @Pattern(
            regexp = "(?!PasswordForJanuary|PasswordForFebruary|PasswordForMarch|PasswordForApril|PasswordForMay|PasswordForJune|PasswordForJuly|PasswordForAugust|PasswordForSeptember|PasswordForOctober|PasswordForNovember|PasswordForDecember)(\\w+)",
            message = "The password is in the hacker's database!")*/
    @Transient
    private String password;

}
