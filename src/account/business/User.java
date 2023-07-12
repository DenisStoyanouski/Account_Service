package account.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "\"user\"")
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
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column
    private String lastname;
    @Pattern(regexp = "\\w+(@acme.com)$", message = "The email domain must be acme.com")
    @Email(message = "")
    private String email;
    @NotBlank
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @Pattern(
            regexp = "(?!PasswordForJanuary|PasswordForFebruary|PasswordForMarch|PasswordForApril|PasswordForMay|PasswordForJune|PasswordForJuly|PasswordForAugust|PasswordForSeptember|PasswordForOctober|PasswordForNovember|PasswordForDecember)(\\w+)",
            message = "The password is in the hacker's database!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    private String password;

}
