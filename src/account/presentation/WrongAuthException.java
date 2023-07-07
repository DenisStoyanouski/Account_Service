package account.presentation;

import org.springframework.security.core.AuthenticationException;

public class WrongAuthException extends AuthenticationException {
    public WrongAuthException (String message) {
        super(message);
    }
}
