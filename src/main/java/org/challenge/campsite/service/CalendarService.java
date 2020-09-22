package org.challenge.campsite.service;

import org.challenge.campsite.entity.Calendar;
import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.repository.CalendarRepository;
import org.challenge.campsite.validator.DateValidator;
import org.challenge.campsite.vo.CalendarVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    @Value("${campsite.max_visitors_day}")
    private int MAX_VISITORS_DAYS;

    @Value("${campsite.max_reservation_days}")
    private int MAX_RESERVATION_DAYS;

    DateValidator dateValidator;

    CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository, DateValidator dateValidator){
        this.calendarRepository = calendarRepository;
        this.dateValidator = dateValidator;
    }

    public List<CalendarVO> checkAvailability(LocalDate startDate, LocalDate endDate) {
        DateValidator.validateDates(startDate,endDate);
        List<Calendar> calendarList = calendarRepository.findAllByDateBetweenAndTotalVisitorsIsLessThan(startDate, endDate,MAX_VISITORS_DAYS);
        return CalendarVO.getListCalendarVO(calendarList);
    }


//	@Transactional
//	@Retryable(value = { BatchUpdateException.class, DataIntegrityViolationException.class,
//			ObjectOptimisticLockingFailureException.class }, maxAttempts = MAX_ATTEMPTS)


/*    private long calculateDaysInAdvance(LocalDate startDate, LocalDate endDate) {
        if(Objects.isNull(startDate))
            startDate = LocalDate.now();
        if (Objects.nonNull(endDate))
            return ChronoUnit.DAYS.between(startDate, endDate);
        endDate = LocalDate.now().plusMonths(1);
        return ChronoUnit.DAYS.between(startDate, endDate);
    }*/


}
