package account.presentation;

import account.business.*;
import account.exception.PasswordException;
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

    final private CredentialsService credentialsService;

    final private UserDTOMapper userDTOMapper;

    final private PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService,
                          CredentialsService credentialsService,
                          UserDTOMapper userDTOMapper, PasswordEncoder encoder) {
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.userDTOMapper = userDTOMapper;
        this.encoder = encoder;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> testAuthentication(Authentication auth) {
        return ResponseEntity.ok().body(userDTOMapper.apply(userService.findUserByEmail(auth.getName())));
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Object> changePassword(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody Map<String, String> newPassword) {
        String candidate = newPassword.get("new_password");
        String username = details.getUsername();
        PasswordValidator.check(candidate);
        if (encoder.matches(candidate, details.getPassword())) {
            throw new PasswordException("The passwords must be different!");
        }
        String pass = encoder.encode(candidate);
        credentialsService.updatePassword(username, pass);
        userService.updatePassword(username, pass);
        Map<String, String> response = new HashMap<>();
        response.put("email", details.getUsername().toLowerCase());
        response.put("status", "The password has been updated successfully");
        return ResponseEntity.ok(response);
    }
}
