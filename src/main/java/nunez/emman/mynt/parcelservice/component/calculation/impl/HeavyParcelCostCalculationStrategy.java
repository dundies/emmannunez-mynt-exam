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
public class HeavyParcelCostCalculationStrategy extends ParcelCostCalculationStrategy {

    @Override
    public ParcelType getResponsibleParcelType() {
        return ParcelType.HEAVY_PARCEL;
    }

    @Override
    public BigDecimal calculateCost(final ParcelInfo parcelInfo) {
        return calculateParcelPerKilogram(parcelInfo.getWeight(), parcelDeliveryProperty.getHeavyParcelCostPerKilogram());
    }

}
