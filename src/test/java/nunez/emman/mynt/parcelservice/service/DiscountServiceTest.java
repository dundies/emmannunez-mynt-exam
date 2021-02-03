package nunez.emman.mynt.parcelservice.service;

import nunez.emman.mynt.parcelservice.TestHelper;
import nunez.emman.mynt.parcelservice.client.VoucherClient;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.ExpiredVoucherException;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private VoucherClient voucherClient;

    @Test
    public void testApplyVoucherDiscount_whenValidVoucher_thenCalculate() throws ParseException, BaseException {
        final String voucherCode = "ABC";
        when(voucherClient.getVoucherItem(anyString())).thenReturn(TestHelper.createValidVoucher(voucherCode));

        final BigDecimal cost = new BigDecimal("100.00");
        final Date currentDate = DateUtils.parseDate("2021-02-03", "yyyy-MM-dd");
        final BigDecimal discountedCost = discountService.applyVoucherDiscount(cost, voucherCode, currentDate);

        assertEquals(0, discountedCost.compareTo(new BigDecimal("90.00")));
    }

    @Test
    public void testApplyVoucherDiscount_whenExpiredVoucher_thenThrowException() throws ParseException, BaseException {
        final String voucherCode = "ABC";
        when(voucherClient.getVoucherItem(anyString())).thenReturn(TestHelper.createExpiredVoucher(voucherCode));

        final BigDecimal cost = new BigDecimal("100.00");
        final Date currentDate = DateUtils.parseDate("2021-02-03", "yyyy-MM-dd");

        assertThrows(ExpiredVoucherException.class, () -> discountService.applyVoucherDiscount(cost, voucherCode, currentDate));
    }

    @Test
    public void testApplyVoucherDiscount_whenEmptyVoucherCode_thenDoNothing() throws ParseException, BaseException {
        final BigDecimal cost = new BigDecimal("100.00");
        final Date currentDate = DateUtils.parseDate("2021-02-03", "yyyy-MM-dd");
        final BigDecimal discountedCost = discountService.applyVoucherDiscount(cost, null, currentDate);
        assertEquals(0, discountedCost.compareTo(cost));
    }

}