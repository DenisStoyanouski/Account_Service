package account.presentation;

public class UsernameIsOccupiedException extends RuntimeException {
    public UsernameIsOccupiedException(String message) {
        super(message);
    }
}
