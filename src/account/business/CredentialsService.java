package account.business;

import account.persistance.CredentialsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CredentialsService implements UserDetailsService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        try {
            userDetails = credentialsRepository.findByUsernameIgnoreCase(username).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        return userDetails;
    }

    @Transactional
    public void updatePassword(String username, String pass) {
        Credentials credentials = credentialsRepository.findByUsernameIgnoreCase(username).get();
        credentials.setPassword(pass);
    }

    public void save(Credentials credentials) {
        credentialsRepository.save(credentials);
    }

    public Optional<Credentials> findDetailsByUsername(String username) {
        return credentialsRepository.findByUsernameIgnoreCase(username);
    }
}
