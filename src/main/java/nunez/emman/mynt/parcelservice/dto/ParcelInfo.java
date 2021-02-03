package nunez.emman.mynt.parcelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Data
@AllArgsConstructor
public class ParcelInfo {

    private Double weight;
    private Double volume;

}
