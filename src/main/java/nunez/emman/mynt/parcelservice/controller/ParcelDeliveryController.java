package nunez.emman.mynt.parcelservice.controller;

import lombok.extern.slf4j.Slf4j;
import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationRequestDto;
import nunez.emman.mynt.parcelservice.dto.ParcelDeliveryCalculationResult;
import nunez.emman.mynt.parcelservice.exceptions.BaseException;
import nunez.emman.mynt.parcelservice.service.ParcelDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@RestController
@RequestMapping("/parcel-delivery")
@Slf4j
public class ParcelDeliveryController {

    @Autowired
    private ParcelDeliveryService parcelDeliveryService;

    @GetMapping("/calculate")
    public ParcelDeliveryCalculationResult calculate(final ParcelDeliveryCalculationRequestDto parcelDeliveryCalculationRequestDto) throws BaseException {
        log.info("Requested calculation of parcel delivery cost with parcel info [{}]...", parcelDeliveryCalculationRequestDto);
        return parcelDeliveryService.calculateCost(parcelDeliveryCalculationRequestDto);
    }

}
