package org.challenge.campsite.repository;

import org.challenge.campsite.entity.Calendar;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Set<Calendar> findAllByDateCalBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    Set<Calendar> findAllByDateCalBetweenAndTotalVisitorsIsLessThan(LocalDate startDate, LocalDate endDate, int maxVisitors);
}
