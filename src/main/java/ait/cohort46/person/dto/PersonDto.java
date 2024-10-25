package ait.cohort46.person.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
public class PersonDto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private AddressDto address;
}
