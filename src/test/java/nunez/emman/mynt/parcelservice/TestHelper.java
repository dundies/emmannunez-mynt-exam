package nunez.emman.mynt.parcelservice;

import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationRequestDto;
import nunez.emman.mynt.parcelservice.dto.voucher.VoucherItem;
import nunez.emman.mynt.parcelservice.properties.ParcelDeliveryProperty;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public class TestHelper {

    public static ParcelDeliveryProperty createBasicParcelDeliveryProperty() {
        return ParcelDeliveryProperty.builder()
                .weightLimit(50.0)
                .heavyParcelExceedWeight(10.0)
                .smallParcelVolumeLimit(1500.0)
                .mediumParcelVolumeLimit(2500.0)
                .heavyParcelCostPerKilogram(20.0)
                .smallParcelCostPerVolume(0.03)
                .mediumParcelCostPerVolume(0.04)
                .largeParcelCostPerVolume(0.05)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createRejectedParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(1000.0)
                .height(10.0)
                .width(10.0)
                .length(10.0)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createInvalidParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(-1000.0)
                .height(-10.0)
                .width(0.0)
                .length(10.0)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createHeavyParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(40.0)
                .height(10.0)
                .width(10.0)
                .length(10.0)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createSmallParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(10.0)
                .height(10.0)
                .width(10.0)
                .length(10.0)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createMediumParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(10.0)
                .height(20.0)
                .width(10.0)
                .length(10.0)
                .build();
    }

    public static ParcelDeliveryCalculationRequestDto createLargeParcelRequest() {
        return ParcelDeliveryCalculationRequestDto.builder()
                .weight(10.0)
                .height(30.0)
                .width(10.0)
                .length(10.0)
                .build();
    }

    public static VoucherItem createValidVoucher(final String voucherCode) {
        return VoucherItem.builder()
                .code(voucherCode)
                .discount(10.0)
                .expiry(new Date())
                .build();
    }

    public static VoucherItem createExpiredVoucher(final String voucherCode) throws ParseException {
        return VoucherItem.builder()
                .code(voucherCode)
                .discount(10.0)
                .expiry(DateUtils.parseDate("2020-01-01","yyyy-MM-dd"))
                .build();
    }

}
