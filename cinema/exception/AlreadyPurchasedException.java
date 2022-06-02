package cinema.exception;

public class AlreadyPurchasedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "That ticket has already been purchased!";
    }
}
