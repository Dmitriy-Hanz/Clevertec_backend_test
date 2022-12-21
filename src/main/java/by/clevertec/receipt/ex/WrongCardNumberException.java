package by.clevertec.receipt.ex;

public class WrongCardNumberException extends RuntimeException{
    public WrongCardNumberException(String message) {
        super(message);
    }
}
