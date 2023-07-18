package account.exception;

public class PaymentExistException extends RuntimeException {
    public PaymentExistException(String message) {
        super(message);
    }
}
