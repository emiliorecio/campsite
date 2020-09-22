package org.challenge.campsite.validator;

import org.challenge.campsite.exception.InvalidRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class DateValidator {


    public static void validateDates(LocalDate startDate, LocalDate endDate) {
        //TODO CHECK  DATES VALIDATION
        if((Objects.nonNull(startDate)) && (startDate.isBefore(LocalDate.now()))){
            throw new InvalidRequest("startDate its before today");
        }
        if((Objects.nonNull(endDate)) && (endDate.isAfter(LocalDate.now().plusMonths(1)))){
            throw new InvalidRequest("endDate must be one month in advance maximum");
        }
        if((Objects.nonNull(endDate)) && ((endDate.isBefore(LocalDate.now().plusDays(1))))){
            throw new InvalidRequest("endDate must be one day in advance");
        }
        if(Objects.nonNull(startDate) && Objects.nonNull(endDate) && endDate.isAfter(startDate))
            throw new InvalidRequest("endDate must be grater than startDate");
    }

    public static void validateConsecutivesDates(LocalDate startDate, LocalDate endDate) {

}
