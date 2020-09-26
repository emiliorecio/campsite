package org.challenge.campsite.service;

import org.challenge.campsite.entity.Reservation;
import org.challenge.campsite.exception.EntityNotFound;
import org.challenge.campsite.repository.ReservationRepository;
import org.challenge.campsite.validator.ReservationValidator;
import org.challenge.campsite.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    @Value("${campsite.max_reservation_days}")
    private int MAX_VISITORS_DAYS;

    private final ReservationRepository reservationRepository;

    private final CalendarService calendarService;

    public ReservationService(ReservationRepository reservationRepository, CalendarService calendarService) {
        this.reservationRepository = reservationRepository;
        this.calendarService = calendarService;
    }

    public ReservationVO getReservation(long id) {
        Reservation reservation = findById(id);
        return ReservationVO.fromEntity(reservation);
    }

    public ReservationVO addUpdateReservation(ReservationVO reservationVO) {
        ReservationValidator.validateReservation(reservationVO);
        Reservation newReservation = ReservationVO.toEntity(reservationVO);
        if (reservationVO.getId() < 1) {
            saveReservation(newReservation);
        } else {
            updateReservation(newReservation);
        }
        return ReservationVO.fromEntity(newReservation);
    }


    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        calendarService.addVisitors(reservation.getCheckIn(), reservation.getCheckOut(), reservation.getTotalGroup());
        return reservationRepository.save(reservation);
    }


    @Transactional
    public Reservation updateReservation(Reservation reservation) {
        Reservation oldReservation = findById(reservation.getId());
        if(hasChangeDatesOrTotalGroup(reservation, oldReservation)) {
            calendarService.updateVisitors(reservation.getCheckIn(), reservation.getCheckOut(), reservation.getTotalGroup(), oldReservation.getCheckIn(), oldReservation.getCheckOut(), oldReservation.getTotalGroup());
        }
        updateReservationEntity(oldReservation, reservation);
        return reservationRepository.save(oldReservation);
    }


    @Transactional
    public void deleteReservation(long id) {
        Reservation reservation = findById(id);
        calendarService.removeVisitors(reservation.getCheckIn(), reservation.getCheckOut(), reservation.getTotalGroup());
        reservationRepository.delete(reservation);
    }

    private void updateReservationEntity(Reservation oldReservation, Reservation reservation) {
        oldReservation.setTotalGroup(reservation.getTotalGroup());
        oldReservation.setNameVisitor(reservation.getNameVisitor());
        oldReservation.setEmail(reservation.getEmail());
        oldReservation.setCheckOut(reservation.getCheckOut());
        oldReservation.setCheckIn(reservation.getCheckIn());
    }

    private boolean hasChangeDatesOrTotalGroup(Reservation reservation, Reservation oldReservation) {
        if (!reservation.getCheckIn().isEqual(oldReservation.getCheckIn()))
            return true;
        if (!reservation.getCheckOut().isEqual(oldReservation.getCheckOut()))
            return true;
        if(!(reservation.getTotalGroup() == oldReservation.getTotalGroup()))
            return true;
        return false;
    }

    private Reservation findById(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFound("Entity not found"));
    }

}
