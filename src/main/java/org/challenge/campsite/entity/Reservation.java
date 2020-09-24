package org.challenge.campsite.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    private LocalDate checkIn;

    @Future
    private LocalDate checkOut;

    @NotBlank
    private String nameVisitor;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Range(min = 1)
    private Integer totalGroup;

}
