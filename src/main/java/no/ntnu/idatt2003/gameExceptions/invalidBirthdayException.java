package no.ntnu.idatt2003.gameExceptions;

/**
 * Exception for invalid birthday.
 */
public class invalidBirthdayException extends Exception {

  /**
   * Exception for invalid birthday.
   *
   * @param message the message to be displayed
   */
  public invalidBirthdayException(String message) {
    super("That is an invalid birthday: " + message);
  }
}