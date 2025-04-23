package no.ntnu.idatt2003.gameExceptions;

public class invalidBirthdayException extends Exception{
    
    public invalidBirthdayException(String message){
        super("That is an invalid birthday: " + message);
    }
}