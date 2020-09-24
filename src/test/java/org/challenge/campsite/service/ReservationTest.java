package org.challenge.campsite.service;

import org.challenge.campsite.entity.Reservation;
import org.challenge.campsite.repository.ReservationRepository;
import org.challenge.campsite.vo.ReservationVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ReservationTest {
    @TestConfiguration
    static class ReservationTestContextConfiguration {

        @Bean
        public ReservationService reservationService(ReservationRepository reservationRepository, CalendarService calendarService) {
            return new ReservationService(reservationRepository, calendarService);
        }
    }

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCheckIn(LocalDate.now());
        reservation.setCheckOut(LocalDate.now().plusMonths(1));
/*        Visitor visitor = new Visitor();
        visitor.setEmail("testemail@gmail.com");
        visitor.setFamilyGroup(1);
        visitor.setId(1L);
        visitor.setNameVisitor("Name Visitor");
        reservation.setVisitor(visitor);*/

        Mockito.when(reservationRepository.findById(reservation.getId()))
                .thenReturn(Optional.of(reservation));
    }

    @Test
    public void findReservationById() {
        long id = 1;
        ReservationVO found = reservationService.getReservation(id);
       // Assert.assertEquals("Name Visitor", found.getVisitor().getNameVisitor());
    }

}
