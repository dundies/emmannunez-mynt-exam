package nunez.emman.mynt.parcelservice.dto.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Data
public class VoucherItem {

    private Double discount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expiry;

}
