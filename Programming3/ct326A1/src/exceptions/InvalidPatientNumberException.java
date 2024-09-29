package exceptions;
//ciaran gray 22427722

public class InvalidPatientNumberException extends Exception{
    public InvalidPatientNumberException(String message) {
        super(message);
    }
}
