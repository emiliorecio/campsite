package org.challenge.campsite.service;

import org.challenge.campsite.entity.Calendar;
import org.challenge.campsite.exception.ReservationException;
import org.challenge.campsite.repository.CalendarRepository;
import org.challenge.campsite.validator.DateValidator;
import org.challenge.campsite.vo.CalendarVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CalendarService {

    DateValidator dateValidator;
    CalendarRepository calendarRepository;
    @Value("${campsite.max_visitors_day}")
    private int MAX_VISITORS_DAYS;
    private final Sort sortDateCal = Sort.by(Sort.Direction.ASC, "dateCal");

    public CalendarService(CalendarRepository calendarRepository, DateValidator dateValidator) {
        this.calendarRepository = calendarRepository;
        this.dateValidator = dateValidator;
    }

    public Set<CalendarVO> checkAvailability(LocalDate startDate, LocalDate endDate) {
        DateValidator.validateDates(startDate, endDate);
        Set<Calendar> calendarList = calendarRepository.findAllByDateCalBetweenAndTotalVisitorsIsLessThan(startDate, endDate, MAX_VISITORS_DAYS);
        return CalendarVO.getListCalendarVO(calendarList);
    }

    public void addVisitors(LocalDate checkIn, LocalDate checkOut, Integer totalGroup) {
        Set<Calendar> calendarList = calendarRepository.findAllByDateCalBetween(checkIn, checkOut, sortDateCal);
        for (Calendar calendar : calendarList) {
            checkMaxVisitors(calendar.getTotalVisitors() + totalGroup);
            calendar.setTotalVisitors(calendar.getTotalVisitors() + totalGroup);
        }
        calendarRepository.saveAll(calendarList);
    }

    public void updateVisitors(LocalDate checkIn, LocalDate checkOut, Integer totalGroup, LocalDate oldCheckIn, LocalDate oldCheckOut, Integer oldTotalGroup) {
        Set<Calendar> newCalendarList = calendarRepository.findAllByDateCalBetween(checkIn, checkOut, sortDateCal);
        Set<Calendar> oldCalendarList = calendarRepository.findAllByDateCalBetween(oldCheckIn, oldCheckOut, sortDateCal);
        Set<Calendar> uniqueNewCalendarList = getUniqueCalendarNewList(newCalendarList, oldCalendarList);
        for (Calendar calendar : oldCalendarList) {
            if (newCalendarList.contains(calendar)) {
                checkMaxVisitors(calendar.getTotalVisitors() - oldTotalGroup + totalGroup);
                calendar.setTotalVisitors(calendar.getTotalVisitors() - oldTotalGroup + totalGroup);
            } else {
                calendar.setTotalVisitors(calendar.getTotalVisitors() - oldTotalGroup);
            }
        }
        for (Calendar calendar : uniqueNewCalendarList) {
                checkMaxVisitors(calendar.getTotalVisitors() + totalGroup);
                calendar.setTotalVisitors(calendar.getTotalVisitors() + totalGroup);
                oldCalendarList.add(calendar);
        }
        calendarRepository.saveAll(oldCalendarList);
    }

    private Set<Calendar> getUniqueCalendarNewList(Set<Calendar> newCalendarList, Set<Calendar> oldCalendarList) {
        Set<Calendar> result = new HashSet<>();
        for (Calendar calendar : newCalendarList) {
            if (!oldCalendarList.contains(calendar)) {
                result.add(calendar);
            }
        }
        return result;
    }

    private boolean checkMaxVisitors(int totalVisitors) {
        if (totalVisitors > MAX_VISITORS_DAYS) {
            throw new ReservationException("Cant create reservation, max occupancy surpassed");
        }
        return true;
    }

    public void removeVisitors(LocalDate checkIn, LocalDate checkOut, Integer totalGroup) {
        Set<Calendar> calendarList = calendarRepository.findAllByDateCalBetween(checkIn, checkOut, sortDateCal);
        for (Calendar calendar : calendarList) {
            calendar.setTotalVisitors(calendar.getTotalVisitors() - totalGroup);
        }
        calendarRepository.saveAll(calendarList);
    }
}
