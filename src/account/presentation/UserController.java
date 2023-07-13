package account.presentation;

import account.business.PasswordValidator;
import account.business.User;
import account.business.UserDetailsImpl;
import account.business.UserService;
import account.exception.PasswordException;
import account.exception.UsernameIsOccupiedException;
import account.exception.UsernameNotFoundException;
import account.persistance.UserDetailsRepository;
import account.persistance.UserRepository;
import jakarta.validation.Valid;
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

    final private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> testAuthentication(Authentication auth) {
        return ResponseEntity.ok().body(userService.findUserByEmail(auth.getName()));
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Object> changePassword(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody Map<String, String> newPassword) {
        String candidate = newPassword.get("new_password");
        User user = userService.changePassword(details, candidate);
        Map<String, String> response = new HashMap<>();
        response.put("email", user.getEmail().toLowerCase());
        response.put("status", "The password has been updated successfully");
        return ResponseEntity.ok(response);
    }
}
