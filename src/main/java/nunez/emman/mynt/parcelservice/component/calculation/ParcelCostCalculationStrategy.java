package nunez.emman.mynt.parcelservice.component.calculation;

import nunez.emman.mynt.parcelservice.dto.ParcelInfo;
import nunez.emman.mynt.parcelservice.enums.ParcelType;
import nunez.emman.mynt.parcelservice.properties.ParcelDeliveryProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
public abstract class ParcelCostCalculationStrategy {

    protected ParcelDeliveryProperty parcelDeliveryProperty;

    @Autowired
    public void setParcelDeliveryProperty(final ParcelDeliveryProperty parcelDeliveryProperty) {
        this.parcelDeliveryProperty = parcelDeliveryProperty;
    }

    public abstract ParcelType getResponsibleParcelType();

    public abstract BigDecimal calculateCost(final ParcelInfo parcelInfo);

    protected BigDecimal calculateParcelPerKilogram(final Double weight, final Double costPerKilogram) {
        return BigDecimal.valueOf(weight * costPerKilogram);
    }

    protected BigDecimal calculateParcelPerVolume(final Double volume, final Double costPerVolume) {
        return BigDecimal.valueOf(volume * costPerVolume);
    }

}
