package nunez.emman.mynt.parcelservice.service;

import nunez.emman.mynt.parcelservice.TestHelper;
import nunez.emman.mynt.parcelservice.component.calculation.ParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.component.calculation.impl.HeavyParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.component.calculation.impl.LargeParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.component.calculation.impl.MediumParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.component.calculation.impl.SmallParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationRequestDto;
import nunez.emman.mynt.parcelservice.enums.ParcelType;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.IncompleteParcelInfoException;
import nunez.emman.mynt.parcelservice.exceptions.InvalidParcelInfoException;
import nunez.emman.mynt.parcelservice.exceptions.ParcelExceedWeightLimitException;
import nunez.emman.mynt.parcelservice.properties.ParcelDeliveryProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelDeliveryServiceTest {

    private ParcelDeliveryService parcelDeliveryService;

    private List<ParcelCostCalculationStrategy> costCalculationStrategies;

    private ParcelDeliveryProperty parcelDeliveryProperty;

    private DiscountService discountService;

    private HeavyParcelCostCalculationStrategy heavyParcelCostCalculationStrategy;
    private SmallParcelCostCalculationStrategy smallParcelCostCalculationStrategy;
    private MediumParcelCostCalculationStrategy mediumParcelCostCalculationStrategy;
    private LargeParcelCostCalculationStrategy largeParcelCostCalculationStrategy;

    @BeforeEach
    public void init() {
        setupMockCalculationStrategies();
        costCalculationStrategies = Arrays.asList(heavyParcelCostCalculationStrategy, smallParcelCostCalculationStrategy, mediumParcelCostCalculationStrategy, largeParcelCostCalculationStrategy);
        discountService = mock(DiscountService.class);
        parcelDeliveryProperty = TestHelper.createBasicParcelDeliveryProperty();
        parcelDeliveryService = new ParcelDeliveryService(parcelDeliveryProperty, costCalculationStrategies, discountService, null);
        parcelDeliveryService.postConstruct();
    }

    @Test
    public void testCalculateCost_whenIncompleteRequest_thenThrowException() {
        assertThrows(IncompleteParcelInfoException.class, () -> parcelDeliveryService.calculateCost(new ParcelDeliveryCalculationRequestDto()));
    }

    @Test
    public void testCalculateCost_whenInvalidRequest_thenThrowException() {
        assertThrows(InvalidParcelInfoException.class, () -> parcelDeliveryService.calculateCost(TestHelper.createInvalidParcelRequest()));
    }

    @Test
    public void testCalculateCost_whenExceedLimit_thenThrowException() {
        assertThrows(ParcelExceedWeightLimitException.class, () -> parcelDeliveryService.calculateCost(TestHelper.createRejectedParcelRequest()));
    }

    @Test
    public void testCalculateCost_whenHeavyParcel_thenCalculate() throws BaseException {
        when(heavyParcelCostCalculationStrategy.calculateCost(any())).thenReturn(new BigDecimal("100.00"));
        final BigDecimal cost = parcelDeliveryService.calculateCost(TestHelper.createHeavyParcelRequest())
                .getCost();
        assertEquals(0, cost.compareTo(new BigDecimal("100.00")));
        verify(heavyParcelCostCalculationStrategy, times(1)).calculateCost(any());
    }

    @Test
    public void testCalculateCost_whenSmallParcel_thenCalculate() throws BaseException {
        when(smallParcelCostCalculationStrategy.calculateCost(any())).thenReturn(new BigDecimal("100.00"));
        final BigDecimal cost = parcelDeliveryService.calculateCost(TestHelper.createSmallParcelRequest())
                .getCost();
        assertEquals(0, cost.compareTo(new BigDecimal("100.00")));
        verify(smallParcelCostCalculationStrategy, times(1)).calculateCost(any());
    }

    @Test
    public void testCalculateCost_whenMediumParcel_thenCalculate() throws BaseException {
        when(mediumParcelCostCalculationStrategy.calculateCost(any())).thenReturn(new BigDecimal("100.00"));
        final BigDecimal cost = parcelDeliveryService.calculateCost(TestHelper.createMediumParcelRequest())
                .getCost();
        assertEquals(0, cost.compareTo(new BigDecimal("100.00")));
        verify(mediumParcelCostCalculationStrategy, times(1)).calculateCost(any());
    }

    @Test
    public void testCalculateCost_whenLargeParcel_thenCalculate() throws BaseException {
        when(largeParcelCostCalculationStrategy.calculateCost(any())).thenReturn(new BigDecimal("100.00"));

        final BigDecimal cost = parcelDeliveryService.calculateCost(TestHelper.createLargeParcelRequest())
                .getCost();
        assertEquals(0, cost.compareTo(new BigDecimal("100.00")));
        verify(largeParcelCostCalculationStrategy, times(1)).calculateCost(any());
    }

    @Test
    public void testCalculateCostLargeParcel_whenWithDiscount_thenReturnWithDiscount() throws BaseException {
        when(largeParcelCostCalculationStrategy.calculateCost(any())).thenReturn(new BigDecimal("100.00"));

        final String voucherCode = "ABC";
        when(discountService.applyVoucherDiscount(any(), anyString(), any())).thenReturn(new BigDecimal("50.0"));
        final ParcelDeliveryCalculationRequestDto largeParcelRequest = TestHelper.createLargeParcelRequest();
        largeParcelRequest.setVoucherCode(voucherCode);

        final BigDecimal cost = parcelDeliveryService.calculateCost(largeParcelRequest)
                .getCost();

        assertEquals(0, cost.compareTo(new BigDecimal("50.00")));
        verify(largeParcelCostCalculationStrategy, times(1)).calculateCost(any());

        final ArgumentCaptor<String> voucherCaptor = ArgumentCaptor.forClass(String.class);
        verify(discountService, times(1)).applyVoucherDiscount(any(), voucherCaptor.capture(), any());
        assertEquals(voucherCode, voucherCaptor.getValue());
    }

    private void setupMockCalculationStrategies() {
        heavyParcelCostCalculationStrategy = mock(HeavyParcelCostCalculationStrategy.class);
        when(heavyParcelCostCalculationStrategy.getResponsibleParcelType()).thenReturn(ParcelType.HEAVY_PARCEL);

        smallParcelCostCalculationStrategy = mock(SmallParcelCostCalculationStrategy.class);
        when(smallParcelCostCalculationStrategy.getResponsibleParcelType()).thenReturn(ParcelType.SMALL_PARCEL);

        mediumParcelCostCalculationStrategy = mock(MediumParcelCostCalculationStrategy.class);
        when(mediumParcelCostCalculationStrategy.getResponsibleParcelType()).thenReturn(ParcelType.MEDIUM_PARCEL);

        largeParcelCostCalculationStrategy = mock(LargeParcelCostCalculationStrategy.class);
        when(largeParcelCostCalculationStrategy.getResponsibleParcelType()).thenReturn(ParcelType.LARGE_PARCEL);
    }

}