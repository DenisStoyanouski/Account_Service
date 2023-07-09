package account.presentation;

import account.business.BreachedPassword;
import account.business.User;
import account.business.UserDetailsImpl;
import account.persistance.UserDetailsRepository;
import account.persistance.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    BreachedPassword breachedPassword;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        if (breachedPassword.isBreached(user.getPassword())) {
            throw new PasswordException("The password is in the hacker's database!");
        }
        if (user.getPassword().length() < 12) {
            throw new PasswordException("The password length must be at least 12 chars!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new UsernameIsOccupiedException("User exist!");
        }
        userDetailsRepository.save(new UserDetailsImpl(user));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> testAuthentication(Authentication auth) {
        if (userDetailsRepository.findByUsernameIgnoreCase(auth.getName()).isEmpty()) {
            throw new UsernameNotFoundException("Not found");
        }
        return ResponseEntity.ok().body(userRepository.findByEmailIgnoreCase(auth.getName()));
    }
}
