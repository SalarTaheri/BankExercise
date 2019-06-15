package exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("target account not found");
    }
}
