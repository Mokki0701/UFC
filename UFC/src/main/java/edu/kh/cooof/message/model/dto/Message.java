package edu.kh.cooof.message.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	private int messageNo;
	private String messageDate;
	private String seenDate;
	private String messageTitle;
	private String messageContent;
	private int messageStatus;
	private int messageSen;
	private int messageRev;
	
	private String memberEmail;
	
}
