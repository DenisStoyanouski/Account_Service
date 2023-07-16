package account.business;

import account.exception.UsernameIsOccupiedException;
import account.exception.UsernameNotFoundException;
import account.persistance.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CredentialsService credentialsService;

    @Autowired
    public UserService(PasswordEncoder encoder,
                       UserRepository userRepository,
                       CredentialsService credentialsService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.credentialsService = credentialsService;
    }

    public void addNewUser(User user) {
        String userPassword = user.getPassword();
        PasswordValidator.check(userPassword);
        user.setPassword(encoder.encode(userPassword));
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new UsernameIsOccupiedException("User exist!");
        }
        credentialsService.save(new Credentials(user));
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        if (credentialsService.findDetailsByUsername(email).isEmpty()) {
            throw new UsernameNotFoundException("Not found");
        }
        return userRepository.findByEmailIgnoreCase(email).get();
    }
}
