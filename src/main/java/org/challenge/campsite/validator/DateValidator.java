package org.challenge.campsite.validator;

import org.challenge.campsite.exception.InvalidRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class DateValidator {

    private static int MAX_RESERVATION_DAYS;
    private static int MONTH_IN_ADVANCE;
    @Value("${campsite.max_reservation_days}")
    private int max_reservation_days;
    @Value("${campsite.month_in_advance}")
    private int month_in_advance;

    public static void validateDates(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate) || ((Objects.isNull(endDate)))) {
            throw new InvalidRequest("startDate and endDate can't be null");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidRequest("startDate its before today");
        }
        if (endDate.isAfter(LocalDate.now().plusMonths(MONTH_IN_ADVANCE))) {
            throw new InvalidRequest("endDate must be one month in advance from now as much");
        }
        if (startDate.isAfter(endDate))
            throw new InvalidRequest("endDate must be grater than startDate");
    }

    public static void validateConsecutiveDates(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        long daysBetween = 1 + (Period.between(startDate, endDate).getDays());
        if (daysBetween > MAX_RESERVATION_DAYS) {
            throw new InvalidRequest("The max days allowed to reserve are " + MAX_RESERVATION_DAYS + "days");
        }

    }

    @Value("${campsite.month_in_advance}")
    public void setMonth_in_advance(int month) {
        MONTH_IN_ADVANCE = month;
    }

    @Value("${campsite.max_reservation_days}")
    public void setMax_reservation_days(int max) {
        MAX_RESERVATION_DAYS = max;
    }
}
