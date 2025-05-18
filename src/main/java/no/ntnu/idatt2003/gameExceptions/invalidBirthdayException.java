package no.ntnu.idatt2003.gameExceptions;

/**
 * Exception class for handling invalid birthday inputs.
 * This exception is thrown when the user inputs an invalid birthday.
 */
public class invalidBirthdayException extends Exception{
    
    public invalidBirthdayException(String message){
        super("That is an invalid birthday: " + message);
    }
}