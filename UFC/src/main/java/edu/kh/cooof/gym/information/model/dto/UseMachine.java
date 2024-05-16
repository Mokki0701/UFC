package edu.kh.cooof.gym.information.model.dto;

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
public class UseMachine {
	
    private int id;

    private String name;
    private String imageUrl;
    private String videoUrl;
    private String targetMuscle;
    private String method;
    private String precautions;
	
}
