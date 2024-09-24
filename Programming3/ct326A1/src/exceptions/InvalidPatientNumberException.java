package exceptions;

public class InvalidPatientNumberException extends Exception{
    public InvalidPatientNumberException(String message) {
        super(message);
    }
}
