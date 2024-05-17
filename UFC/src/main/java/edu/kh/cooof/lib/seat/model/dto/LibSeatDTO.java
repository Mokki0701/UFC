package edu.kh.cooof.lib.seat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibSeatDTO {
    private int seatNo;
    private int xCoordi;
    private int yCoordi;
    private int condition;
}
