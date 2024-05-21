package edu.kh.cooof.gym.application.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Application {
	

    private int applicationNo;
    private int memberNo;
    private String position;
    private String applyRoute;
    private String resumePath;
    private Date applyDate;
	
}
