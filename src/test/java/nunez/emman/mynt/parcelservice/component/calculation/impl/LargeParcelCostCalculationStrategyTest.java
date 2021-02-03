package nunez.emman.mynt.parcelservice.component.calculation.impl;

import nunez.emman.mynt.parcelservice.TestHelper;
import nunez.emman.mynt.parcelservice.dto.ParcelInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LargeParcelCostCalculationStrategyTest {

    private LargeParcelCostCalculationStrategy largeParcelCostCalculationStrategy;

    @BeforeAll
    public void init() {
        largeParcelCostCalculationStrategy = new LargeParcelCostCalculationStrategy();
        largeParcelCostCalculationStrategy.setParcelDeliveryProperty(TestHelper.createBasicParcelDeliveryProperty());
    }

    @Test
    public void testCalculateCost() {
        final BigDecimal cost = largeParcelCostCalculationStrategy.calculateCost(new ParcelInfo(20.0, 100.0));
        assertEquals(0, cost.compareTo(new BigDecimal("5.0")));
    }

    @Test
    public void testCalculateCostIncorrect() {
        final BigDecimal cost = largeParcelCostCalculationStrategy.calculateCost(new ParcelInfo(20.0, 100.0));
        assertEquals(1, cost.compareTo(new BigDecimal("2.0")));
    }

}