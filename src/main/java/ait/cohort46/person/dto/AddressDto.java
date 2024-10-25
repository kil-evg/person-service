package ait.cohort46.person.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class AddressDto {
    private String city;
    private String street;
    private Integer building;
}
