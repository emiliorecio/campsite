package org.challenge.campsite.service;

import org.challenge.campsite.entity.Reservation;
import org.challenge.campsite.exception.EntityNotFound;
import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.repository.ReservationRepository;
import org.challenge.campsite.validator.DateValidator;
import org.challenge.campsite.validator.ReservationValidator;
import org.challenge.campsite.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    @Value("${campsite.max_reservation_days}")
    private int MAX_VISITORS_DAYS;

    private ReservationRepository reservationRepository;

    private CalendarService calendarService;

    public ReservationService(ReservationRepository reservationRepository, CalendarService calendarService) {
        this.reservationRepository = reservationRepository;
        this.calendarService = calendarService;
    }

    public ReservationVO getReservation(long id) {
        Reservation reservation = findById(id).orElseThrow(() -> new EntityNotFound("Entity not found"));
        return ReservationVO.fromEntity(reservation);
    }

    //@Transactional
    public ReservationVO addUpdateReservation(ReservationVO reservationVO) {
        DateValidator.validateDates(reservationVO.getCheckIn(), reservationVO.getCheckOut());
        ReservationValidator.validateReservation(reservationVO);
        Reservation newReservation = ReservationVO.toEntity(reservationVO);
        if(reservationVO.getId() < 1){
            System.out.println("SAVING");
            saveReservation(newReservation);
        }else{
            System.out.println("UPDATING");
            updateReservation(newReservation);
        }
        return ReservationVO.fromEntity(newReservation);
    }




    @Transactional
    public Reservation saveReservation(Reservation reservation) {


        return reservationRepository.save(reservation);
    }


    @Transactional
    public Reservation updateReservation(Reservation reservation) {
        //return saveReservation(reservation);
        return null;
    }

    private Optional<Reservation> findById(long id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public void deleteReservation(long id) {
        Reservation reservation = findById(id).orElseThrow(() -> new EntityNotFound("Entity not found"));
        reservationRepository.delete(reservation);
    }

}
