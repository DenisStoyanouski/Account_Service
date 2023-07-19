package account.presentation;

import account.business.*;
import account.exception.PasswordException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    private final UserService userService;
    private final CredentialsService credentialsService;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserController(UserService userService,
                          CredentialsService credentialsService,
                          UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Object> changePassword(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody
            Map<String, String> newPassword) {
        String candidate = newPassword.get("new_password");
        String username = details.getUsername();
        credentialsService.updatePassword(username, candidate);
        Map<String, String> response = new HashMap<>();
        response.put("email", details.getUsername().toLowerCase());
        response.put("status", "The password has been updated successfully");
        return ResponseEntity.ok(response);
    }
}
