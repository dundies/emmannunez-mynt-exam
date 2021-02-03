package nunez.emman.mynt.parcelservice.client;

import nunez.emman.mynt.parcelservice.TestHelper;
import nunez.emman.mynt.parcelservice.dto.voucher.VoucherItem;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.InvalidVoucherCodeException;
import nunez.emman.mynt.parcelservice.properties.VoucherApiProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherClientTest {

    @InjectMocks
    private VoucherClient voucherClient;

    @Mock
    private VoucherApiProperty voucherApiProperty;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetVoucherItem() throws BaseException {
        final String voucherCode = "ABC";
        final ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        when(voucherApiProperty.getVoucherEndpoint()).thenReturn("http://test.com/voucher");
        when(voucherApiProperty.getKey()).thenReturn("testkey");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(TestHelper.createValidVoucher(voucherCode));

        final VoucherItem voucherItem = voucherClient.getVoucherItem(voucherCode);
        assertNotNull(voucherItem);
        assertEquals(10.0, voucherItem.getDiscount());

        verify(restTemplate, times(1)).getForObject(uriCaptor.capture(), any());
        assertEquals("http://test.com/voucher/" + voucherCode + "?key=testkey", uriCaptor.getValue());

    }

    @Test
    public void testGetVoucherItem_whenBadRequest_thenThrowException() {
        doThrow(HttpClientErrorException.BadRequest.create("test", HttpStatus.BAD_REQUEST, "test", new HttpHeaders(), null, null)).when(restTemplate).getForObject(anyString(), any());
        assertThrows(InvalidVoucherCodeException.class, () -> voucherClient.getVoucherItem("Test"));

    }

    @Test
    public void testGetVoucher_whenUnexpectedVoucherApiError_thenThrowException() {
        doThrow(new RuntimeException()).when(restTemplate).getForObject(anyString(), any());
        assertThrows(BaseException.class, () -> voucherClient.getVoucherItem("Test"));

    }

}