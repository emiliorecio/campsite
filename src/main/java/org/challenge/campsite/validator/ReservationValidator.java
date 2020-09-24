package org.challenge.campsite.validator;

import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {

    @Value("${campsite.max_visitors_day}")
    private static int MAX_VISITORS_DAYS;

    public static void validateReservation(ReservationVO vo) {
        if ((vo.getTotalGroup() < 1) || (vo.getTotalGroup() > MAX_VISITORS_DAYS)) {
            throw new InvalidRequest(String.format("Family group must be between 1 and %s", MAX_VISITORS_DAYS));
        }

        DateValidator.validateConsecutiveDates(vo.getCheckIn(), vo.getCheckOut());
    }

    @Value("${campsite.max_visitors_day}")
    public void setMax_visitors_days(int max) {
        MAX_VISITORS_DAYS = max;
    }
}
