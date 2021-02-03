package nunez.emman.mynt.parcelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@AllArgsConstructor
@Data
public class ParcelDeliveryCalculationResult {

    private BigDecimal cost;
    private String currency;

}
