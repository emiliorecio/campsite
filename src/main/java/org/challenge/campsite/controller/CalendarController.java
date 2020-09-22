package org.challenge.campsite.controller;

import org.challenge.campsite.service.CalendarService;
import org.challenge.campsite.vo.CalendarVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private static final String ISO_DATE = "yyyy-mm-dd";

    private static final String DEFAULT_MEDIA = MediaType.APPLICATION_JSON_VALUE;

    private CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(produces = {DEFAULT_MEDIA}, consumes = {DEFAULT_MEDIA})
    public ResponseEntity<List<CalendarVO>> checkAvailability(
            @DateTimeFormat(pattern = ISO_DATE) @RequestParam(required = false) LocalDate startDate,
            @DateTimeFormat(pattern = ISO_DATE) @RequestParam(required = false) LocalDate endDate) {
        return new ResponseEntity<>(calendarService.checkAvailability(startDate, endDate), HttpStatus.OK);
    }


}
