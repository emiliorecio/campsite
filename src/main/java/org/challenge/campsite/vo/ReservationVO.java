package org.challenge.campsite.vo;

import lombok.Data;
import org.challenge.campsite.entity.Reservation;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class ReservationVO implements Serializable {

    private static final long serialVersionUID = 3246365658825988822L;

    private long id;

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotBlank
    private String nameVisitor;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Range(min = 1)
    private Integer totalGroup;


    public static ReservationVO fromEntity(Reservation entity) {
        ReservationVO vo = new ReservationVO();
        if (Objects.nonNull(entity.getId())) {
            vo.setId(entity.getId());
        }

        vo.setCheckIn(entity.getCheckIn());
        vo.setCheckOut(entity.getCheckOut());
        vo.setEmail(entity.getEmail());
        vo.setNameVisitor(entity.getNameVisitor());
        vo.setTotalGroup(entity.getTotalGroup());
        return vo;
    }

    public static Reservation toEntity(ReservationVO vo) {
        Reservation entity = new Reservation();
        if (vo.getId() != 0)
            entity.setId(vo.getId());
        entity.setCheckIn(vo.getCheckIn());
        entity.setCheckOut(vo.getCheckOut());
        entity.setEmail(vo.getEmail());
        entity.setNameVisitor(vo.getNameVisitor());
        entity.setTotalGroup(vo.getTotalGroup());
        return entity;
    }
}