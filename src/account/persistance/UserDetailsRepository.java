package account.persistance;

import account.business.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findByUsernameIgnoreCase(String email);
    Optional<UserDetailsImpl> findByUsername(String email);
}
