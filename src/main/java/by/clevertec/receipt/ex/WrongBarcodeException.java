package by.clevertec.receipt.ex;

public class WrongBarcodeException extends RuntimeException{
    public WrongBarcodeException(String message) {
        super(message);
    }
}
