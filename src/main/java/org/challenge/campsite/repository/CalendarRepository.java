package org.challenge.campsite.repository;

import org.challenge.campsite.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Calendar> findAllByDateBetweenAndTotalVisitorsIsLessThan(LocalDate startDate, LocalDate endDate, int maxVisitors);
}
