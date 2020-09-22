package org.challenge.campsite.vo;

import lombok.Data;
import org.challenge.campsite.entity.Calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class CalendarVO implements Serializable {

    private static final long serialVersionUID = 2052327167055326878L;

    private long id;

    private LocalDate date;

    private int totalVisitors;

    public static CalendarVO fromEntity(Calendar entity) {
        CalendarVO vo = new CalendarVO();
        if (Objects.nonNull(entity.getId())) {
            vo.setId(entity.getId());
        }
        vo.setDate(entity.getDate());
        vo.setTotalVisitors(entity.getTotalVisitors());
        return vo;
    }

    public static Calendar toEntity(CalendarVO vo) {
        Calendar entity = new Calendar();
        if (vo.getId() != 0)
            entity.setId(vo.getId());
        entity.setDate(vo.getDate());
        entity.setTotalVisitors(vo.getTotalVisitors());
        return entity;
    }

    public static List<CalendarVO> getListCalendarVO(List<Calendar> calendars) {
        List<CalendarVO> calendarVOList;
        calendarVOList = calendars.stream().map(a -> CalendarVO.fromEntity(a)).collect(Collectors.toList());
        return calendarVOList;
    }
}
