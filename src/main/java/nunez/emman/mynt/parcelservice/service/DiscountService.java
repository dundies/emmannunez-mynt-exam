package nunez.emman.mynt.parcelservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nunez.emman.mynt.parcelservice.client.VoucherClient;
import nunez.emman.mynt.parcelservice.dto.voucher.VoucherItem;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.ExpiredVoucherException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */

@Service
@AllArgsConstructor
@Slf4j
public class DiscountService {

    private final VoucherClient voucherClient;

    public BigDecimal applyVoucherDiscount(BigDecimal cost, final String voucherCode, final Date currentDate) throws BaseException {
        if (StringUtils.isBlank(voucherCode)) {
            return cost;
        }

        log.info("Applying voucher code [{}] discount...", voucherCode);

        final VoucherItem voucherItem = voucherClient.getVoucherItem(voucherCode);

        if (isVoucherExpired(voucherItem, currentDate)) {
            throw new ExpiredVoucherException();
        }

        final BigDecimal discountValue = getDiscountValue(cost, voucherItem);
        final BigDecimal discountedCost = cost.subtract(discountValue);

        log.info("Applied discount to [{}] with voucher [{}], discount value [{}]. Result = [{}].", cost, voucherItem, discountValue, discountedCost);

        return discountedCost;
    }

    private boolean isVoucherExpired(final VoucherItem voucherItem, final Date currentDate) {
        return currentDate.after(voucherItem.getExpiry());
    }

    private BigDecimal getDiscountValue(final BigDecimal cost, final VoucherItem voucherItem) {
        return cost.multiply(BigDecimal.valueOf(voucherItem.getDiscount() / 100));
    }

}
