package account.presentation;

import account.business.PasswordValidator;
import account.business.User;
import account.business.UserDetailsImpl;
import account.persistance.UserDetailsRepository;
import account.persistance.UserRepository;
import jakarta.validation.Valid;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


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
        String userPassword = user.getPassword();
        PasswordValidator pv = new PasswordValidator(userPassword);
        pv.check();
        user.setPassword(encoder.encode(userPassword));
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

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Object> changePassword(@AuthenticationPrincipal UserDetails details, @RequestBody Map<String, String> newPassword) {
        if (newPassword.get("new_password") == null || encoder.matches(newPassword.get("new_password"), details.getPassword())) {
            throw new PasswordException("The passwords must be different!");
        }


        Map<String, String> response = new HashMap<>();
        response.put("email", details.getUsername());
        response.put("status", "The password has been updated successfully");
        return ResponseEntity.ok(response);
    }
}
