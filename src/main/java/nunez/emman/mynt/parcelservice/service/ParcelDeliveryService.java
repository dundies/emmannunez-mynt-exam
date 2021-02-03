package nunez.emman.mynt.parcelservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nunez.emman.mynt.parcelservice.component.calculation.ParcelCostCalculationStrategy;
import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationRequestDto;
import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationResult;
import nunez.emman.mynt.parcelservice.dto.ParcelInfo;
import nunez.emman.mynt.parcelservice.enums.ParcelType;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.exceptions.IncompleteParcelInfoException;
import nunez.emman.mynt.parcelservice.exceptions.ParcelExceedWeightLimitException;
import nunez.emman.mynt.parcelservice.properties.ParcelDeliveryProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@Service
@Slf4j
@AllArgsConstructor
public class ParcelDeliveryService {

    private static final String PHP = "PHP";

    private final ParcelDeliveryProperty parcelDeliveryProperty;
    private final List<ParcelCostCalculationStrategy> costCalculationStrategies;
    private final DiscountService discountService;

    private Map<ParcelType, ParcelCostCalculationStrategy> strategyMap;

    @PostConstruct
    public void postConstruct() {
        strategyMap = costCalculationStrategies.stream()
                .collect(Collectors.toMap(ParcelCostCalculationStrategy::getResponsibleParcelType, strategy -> strategy));
    }

    public ParcelDeliveryCalculationResult calculateCost(final ParcelDeliveryCalculationRequestDto parcelDeliveryCalculationRequestDto) throws BaseException {
        validateRequest(parcelDeliveryCalculationRequestDto);

        final Double weight = parcelDeliveryCalculationRequestDto.getWeight();
        if (weight > parcelDeliveryProperty.getWeightLimit()) {
            throw new ParcelExceedWeightLimitException(String.format("Parcel's weight [%s] exceeds weight limit of [%s], parcel rejected.", weight, parcelDeliveryProperty.getWeightLimit()));
        }

        final Double volume = getVolume(parcelDeliveryCalculationRequestDto);
        final String voucherCode = parcelDeliveryCalculationRequestDto.getVoucherCode();
        log.info("Parcel: Weight [{}], Volume [{}], Voucher Code [{}] ", weight, volume, voucherCode);

        final ParcelType parcelType = classifyParcelType(weight, volume);
        log.info("Parcel is classified as [{}].", parcelType);

        BigDecimal cost = strategyMap.get(parcelType).calculateCost(new ParcelInfo(weight, volume));
        log.info("Calculated cost based on parcel type [{}]", cost);

        if (StringUtils.isNotBlank(voucherCode)) {
            cost = discountService.applyVoucherDiscount(cost, voucherCode, new Date());
        }
        cost = cost.setScale(2, RoundingMode.HALF_UP);

        log.info("Parcel delivery cost: PHP [{}]", cost);

        return new ParcelDeliveryCalculationResult(cost, PHP);
    }

    private void validateRequest(final ParcelDeliveryCalculationRequestDto parcelDeliveryCalculationRequestDto) throws IncompleteParcelInfoException {
        if (ObjectUtils.anyNull(parcelDeliveryCalculationRequestDto.getHeight(), parcelDeliveryCalculationRequestDto.getLength(), parcelDeliveryCalculationRequestDto.getWeight(), parcelDeliveryCalculationRequestDto.getWidth())) {
            throw new IncompleteParcelInfoException();
        }
    }

    private Double getVolume(final ParcelDeliveryCalculationRequestDto parcelDeliveryCalculationRequestDto) {
        return parcelDeliveryCalculationRequestDto.getHeight() * parcelDeliveryCalculationRequestDto.getWidth() * parcelDeliveryCalculationRequestDto.getLength();
    }

    private ParcelType classifyParcelType(final Double weight, final Double volume) {
        if (weight > parcelDeliveryProperty.getHeavyParcelExceedWeight()) {
            return ParcelType.HEAVY_PARCEL;

        } else if (volume < parcelDeliveryProperty.getSmallParcelVolumeLimit()) {
            return ParcelType.SMALL_PARCEL;

        } else if (volume < parcelDeliveryProperty.getMediumParcelVolumeLimit()) {
            return ParcelType.MEDIUM_PARCEL;

        } else {
            return ParcelType.LARGE_PARCEL;
        }
    }


}
