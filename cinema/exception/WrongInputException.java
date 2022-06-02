package cinema.exception;

public class WrongInputException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong input!";
    }
}
