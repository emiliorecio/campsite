package org.challenge.campsite.controller;

import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.service.ReservationService;
import org.challenge.campsite.vo.ReservationVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final String DEFAULT_MEDIA = MediaType.APPLICATION_JSON_VALUE;

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "{id}", consumes = {DEFAULT_MEDIA})
    public ResponseEntity<ReservationVO> getReservation(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }


    @PostMapping(produces = {DEFAULT_MEDIA}, consumes = {DEFAULT_MEDIA})
    public ResponseEntity<ReservationVO> addOrUpdateReservation(@Valid @RequestBody ReservationVO reservationVO) {
        return new ResponseEntity<>(reservationService.addUpdateReservation(reservationVO), HttpStatus.OK);
    }


    //TODO MILO
    @DeleteMapping(value = "{id}", produces = {DEFAULT_MEDIA}, consumes = {DEFAULT_MEDIA})
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok(id);
    }

}
