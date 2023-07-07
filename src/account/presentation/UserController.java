package account.presentation;

import account.business.User;
import account.business.UserDetailsImpl;
import account.business.UserDetailsServiceImpl;
import account.persistance.UserDetailsRepository;
import account.persistance.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsernameIsOccupiedException("User exist!");
        }
        userDetailsRepository.save(new UserDetailsImpl(user));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/api/empl/payment/")
    public ResponseEntity<Object> testAuthentication(Authentication auth) {
        if (userDetailsRepository.findByUsername(auth.getName()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(userRepository.findByEmail(auth.getName()));
    }
}
