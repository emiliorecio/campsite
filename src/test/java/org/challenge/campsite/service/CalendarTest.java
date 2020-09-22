package org.challenge.campsite.service;

import org.challenge.campsite.repository.CalendarRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CalendarTest {

    @TestConfiguration
    static class CalendarTestContextConfiguration {

        @Bean
        public CalendarService reservationService(CalendarRepository calendarRepository) {
            //return new CalendarService(calendarRepository);
            return null;
        }
    }

    @Autowired
    private CalendarService calendarService;

    @MockBean
    private CalendarRepository calendarRepository;

    @Before
    public void setUp() {

        /*
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCheckIn(LocalDate.now());
        reservation.setCheckOut(LocalDate.now().plusMonths(1));
        Visitor visitor = new Visitor();
        visitor.setEmail("testemail@gmail.com");
        visitor.setFamilyGroup(1);
        visitor.setId(1L);
        visitor.setNameVisitor("Name Visitor");
        reservation.setVisitor(visitor);

        Mockito.when(reservationRepository.findById(reservation.getId()))
                .thenReturn(Optional.of(reservation));*/
    }
}
