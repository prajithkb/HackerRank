package connect4.exceptions;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(Exception e) {
        super(e);
    }
}
