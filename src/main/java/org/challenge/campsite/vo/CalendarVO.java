package org.challenge.campsite.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.challenge.campsite.entity.Calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CalendarVO implements Serializable {

    private static final long serialVersionUID = 2052327167055326878L;

    @JsonIgnore
    private long id;

    private LocalDate date;

    private int totalVisitors;

    public static CalendarVO fromEntity(Calendar entity) {
        CalendarVO vo = new CalendarVO();
        /*if (Objects.nonNull(entity.getId())) {
            vo.setId(entity.getId());
        }*/
        vo.setDate(entity.getDateCal());
        vo.setTotalVisitors(entity.getTotalVisitors());
        return vo;
    }

    public static Calendar toEntity(CalendarVO vo) {
        Calendar entity = new Calendar();
        if (vo.getId() != 0)
            entity.setId(vo.getId());
        entity.setDateCal(vo.getDate());
        entity.setTotalVisitors(vo.getTotalVisitors());
        return entity;
    }

    public static Set<CalendarVO> getListCalendarVO(Set<Calendar> calendars) {
        Set<CalendarVO> calendarVOList;
        calendarVOList = calendars.stream().map(a -> CalendarVO.fromEntity(a)).collect(Collectors.toCollection(LinkedHashSet::new));
        return calendarVOList;
    }
}
