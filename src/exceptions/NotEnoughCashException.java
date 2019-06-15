package exceptions;

public class NotEnoughCashException extends Exception {
    public NotEnoughCashException() {
        super("no enough cash for transaction");
    }
}
