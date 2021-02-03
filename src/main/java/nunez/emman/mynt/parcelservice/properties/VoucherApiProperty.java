package nunez.emman.mynt.parcelservice.properties;

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
@ConfigurationProperties(prefix = "voucher-api")
public class VoucherApiProperty {

    private String baseUrl;
    private String voucherEndpoint;
    private String key;

}
