package nunez.emman.mynt.parcelservice.component.calculation.impl;

import nunez.emman.mynt.parcelservice.TestHelper;
import nunez.emman.mynt.parcelservice.dto.ParcelInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmallParcelCostCalculationStrategyTest {

    private SmallParcelCostCalculationStrategy smallParcelCostCalculationStrategy;

    @BeforeAll
    public void init() {
        smallParcelCostCalculationStrategy = new SmallParcelCostCalculationStrategy();
        smallParcelCostCalculationStrategy.setParcelDeliveryProperty(TestHelper.createBasicParcelDeliveryProperty());
    }

    @Test
    public void testCalculateCost() {
        final BigDecimal cost = smallParcelCostCalculationStrategy.calculateCost(new ParcelInfo(20.0, 100.0));
        assertEquals(0, cost.compareTo(new BigDecimal("3.0")));
    }

    @Test
    public void testCalculateCostIncorrect() {
        final BigDecimal cost = smallParcelCostCalculationStrategy.calculateCost(new ParcelInfo(20.0, 100.0));
        assertEquals(1, cost.compareTo(new BigDecimal("2.0")));
    }

}