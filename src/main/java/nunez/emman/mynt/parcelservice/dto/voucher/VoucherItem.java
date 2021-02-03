package nunez.emman.mynt.parcelservice.dto.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherItem {

    private String code;

    private Double discount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expiry;

}
