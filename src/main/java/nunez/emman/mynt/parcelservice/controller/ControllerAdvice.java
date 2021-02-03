package nunez.emman.mynt.parcelservice.controller;

import lombok.extern.slf4j.Slf4j;
import nunez.emman.mynt.parcelservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Emman Nunez
 * @date 2/3/2021
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler({InvalidVoucherCodeException.class, ExpiredVoucherException.class, IncompleteParcelInfoException.class, ParcelExceedWeightLimitException.class})
    public ResponseEntity<String> handleBadRequestException(BaseException e) {
        log.error("Encountered bad request error. [{}]", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<String> handleBaseException(BaseException e) {
        log.error("Encountered internal error. [{}]", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unexpected error.", e);
        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
