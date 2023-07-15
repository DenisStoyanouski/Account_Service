package account.persistance;

import account.business.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByUsernameIgnoreCase(String email);
    Optional<Credentials> findByUsername(String email);

}
