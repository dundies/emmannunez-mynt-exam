package nunez.emman.mynt.parcelservice.exceptions;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public class ExpiredVoucherException extends BaseException {

    public ExpiredVoucherException() {
        super("Expired voucher code!");
    }

}
