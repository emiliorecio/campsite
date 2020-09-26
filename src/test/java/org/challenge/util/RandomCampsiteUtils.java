package org.challenge.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.challenge.campsite.entity.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomCampsiteUtils {
    @Value("${campsite.max_visitors_day}")
    private int MAX_VISITORS_DAY;

    @Value("${campsite.month_in_advance}")
    private int MONTH_IN_ADVANCE;

    @Value("${campsite.max_reservation_days}")
    private int MAX_RESERVATION_DAYS;


    public String getRandomUserName() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

    public int getRandomTotalGroup() {
        return RandomUtils.nextInt(1, MAX_VISITORS_DAY + 1);
    }

    public TaskType getRandomTask() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
        for(TaskType taskType: TaskType.values()){
            if (taskType.getTaskType() == randomNum)
                return taskType;
        }
        return TaskType.GET;
    }


    public LocalDate getRandomCheckinDate() {
        LocalDate date = LocalDate.now();
        long startDate = date.toEpochDay();
        long endDate = date.plusMonths(MONTH_IN_ADVANCE).toEpochDay();
        long randomDate = ThreadLocalRandom.current().nextLong(startDate, endDate);
        return LocalDate.ofEpochDay(randomDate);
    }

    public Reservation getRandomReservation() {
        Reservation reservation = new Reservation();
        String userName = getRandomUserName();
        reservation.setNameVisitor(userName);
        reservation.setEmail(userName + "@gmail.com");
        reservation.setTotalGroup(getRandomTotalGroup());
        LocalDate checkIn = getRandomCheckinDate();
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkIn.plusDays(MAX_RESERVATION_DAYS-1));
        return reservation;
    }
}
