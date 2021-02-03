package nunez.emman.mynt.parcelservice.exceptions;

import org.springframework.web.client.HttpClientErrorException;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public class InvalidVoucherCodeException extends BaseException {

    public InvalidVoucherCodeException(final Exception e) {
        super("Invalid voucher code!", e);
    }

}
