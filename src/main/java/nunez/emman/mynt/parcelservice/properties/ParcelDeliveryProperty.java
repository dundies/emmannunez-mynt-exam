package nunez.emman.mynt.parcelservice.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
