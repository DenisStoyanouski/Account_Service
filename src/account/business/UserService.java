package account.business;

import account.exception.PasswordException;
import account.exception.UsernameIsOccupiedException;
import account.exception.UsernameNotFoundException;
import account.persistance.UserDetailsRepository;
import account.persistance.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public UserService(PasswordEncoder encoder,
                       UserRepository userRepository,
                       UserDetailsRepository userDetailsRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public void addNewUser(User user) {
        String userPassword = user.getPassword();
        PasswordValidator.check(userPassword);
        user.setPassword(encoder.encode(userPassword));
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new UsernameIsOccupiedException("User exist!");
        }
        userDetailsRepository.save(new UserDetailsImpl(user));
        userRepository.save(user);
    }

    public UserDetails findUserByEmail(String email) {
        if (userDetailsRepository.findByUsernameIgnoreCase(email).isEmpty()) {
            throw new UsernameNotFoundException("Not found");
        }
        return userDetailsRepository.findByUsernameIgnoreCase(email).get();
    }

    public User changePassword(UserDetails details, String candidate) {
        PasswordValidator.check(candidate);
        if (encoder.matches(candidate, details.getPassword())) {
            throw new PasswordException("The passwords must be different!");
        }
        User user = userRepository.findByEmailIgnoreCase(details.getUsername()).orElseThrow();
        user.setPassword(encoder.encode(candidate));
        userRepository.save(user);
        return user;
    }
}
