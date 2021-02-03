package nunez.emman.mynt.parcelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDeliveryCalculationRequestDto {

    private Double weight;
    private Double height;
    private Double width;
    private Double length;
    private String voucherCode;

}
