package org.challenge.campsite.validator;

import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {

    @Value("${campsite.max_visitors_day}")
    private static int MAX_VISITORS_DAYS;

    public static void  validateReservation(ReservationVO vo) {
        //TODO Improve validation
/*        if(reservationVO.getCheckIn().isAfter(LocalDate.now()))
            throw new InvalidRequest("Checkin date its before today");
        if(reservationVO.getCheckOut().isBefore(LocalDate.now()))
            throw new InvalidRequest("Checkout date its before today");

        if(visitorVO.getEmail().isEmpty()){
            throw new InvalidRequest("Email is empty");
        }
        if((visitorVO.getNameVisitor().isEmpty())){
            throw new InvalidRequest("Name is empty");
        }*/
        if((vo.getTotalGroup() < 1) ||  (vo.getTotalGroup() > MAX_VISITORS_DAYS)){
            throw new InvalidRequest(String.format("Family group must be between 1 and %s", MAX_VISITORS_DAYS));
        }
    }
}
