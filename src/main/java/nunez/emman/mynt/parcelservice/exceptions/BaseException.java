package nunez.emman.mynt.parcelservice.exceptions;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public class BaseException extends Exception {

    public BaseException(final String message) {
        super(message);
    }

    public BaseException(final String message, final Exception exception) {
        super(message, exception);
    }

}
