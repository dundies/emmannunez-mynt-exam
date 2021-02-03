package nunez.emman.mynt.parcelservice.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nunez.emman.mynt.parcelservice.dto.voucher.VoucherItem;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.InvalidVoucherCodeException;
import nunez.emman.mynt.parcelservice.properties.VoucherApiProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Component
@AllArgsConstructor
@Slf4j
public class VoucherClient {

    private final RestTemplate restTemplate;
    private final VoucherApiProperty voucherApiProperty;

    public VoucherItem getVoucherItem(final String voucherCode) throws BaseException {
        try {
            final VoucherItem voucherItem = restTemplate.getForObject(createUri(voucherCode), VoucherItem.class);
            log.info("Retrieved voucher [{}].", voucherItem);
            return voucherItem;

        } catch (HttpClientErrorException.BadRequest e) {
            throw new InvalidVoucherCodeException(e);
        } catch (Exception e) {
            throw new BaseException("Unable to verify voucher!");
        }
    }

    private String createUri(final String voucherCode) {
        return UriComponentsBuilder.fromUriString(voucherApiProperty.getVoucherEndpoint() + "/" + voucherCode)
                .queryParam("key", voucherApiProperty.getKey())
                .toUriString();
    }
}
