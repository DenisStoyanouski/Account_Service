package account.presentation;

public class PasswordBreachedException extends RuntimeException{
    public PasswordBreachedException(String message) {
        super(message);
    }
}
