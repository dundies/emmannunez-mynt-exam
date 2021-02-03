package nunez.emman.mynt.parcelservice.exceptions;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public class ParcelExceedWeightLimitException extends BaseException {

    public ParcelExceedWeightLimitException(final String message) {
        super(message);
    }
}
