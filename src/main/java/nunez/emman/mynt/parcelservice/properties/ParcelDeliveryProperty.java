package nunez.emman.mynt.parcelservice.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "parcel-delivery")
public class ParcelDeliveryProperty {

    private Double weightLimit;
    private Double heavyParcelExceedWeight;
    private Double smallParcelVolumeLimit;
    private Double mediumParcelVolumeLimit;

    private Double heavyParcelCostPerKilogram;
    private Double smallParcelCostPerVolume;
    private Double mediumParcelCostPerVolume;
    private Double largeParcelCostPerVolume;

}
