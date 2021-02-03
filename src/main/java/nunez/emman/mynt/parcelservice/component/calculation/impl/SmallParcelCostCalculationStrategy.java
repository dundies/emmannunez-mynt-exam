package nunez.emman.mynt.parcelservice.component.calculation.impl;

import nunez.emman.mynt.parcelservice.component.calculation.ParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.dto.ParcelInfo;
import nunez.emman.mynt.parcelservice.enums.ParcelType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Component
public class SmallParcelCostCalculationStrategy extends ParcelCostCalculationStrategy {

    @Override
    public ParcelType getResponsibleParcelType() {
        return ParcelType.SMALL_PARCEL;
    }

    @Override
    public BigDecimal calculateCost(final ParcelInfo parcelInfo) {
        return calculateParcelPerVolume(parcelInfo.getVolume(), parcelDeliveryProperty.getSmallParcelCostPerVolume());
    }

}
