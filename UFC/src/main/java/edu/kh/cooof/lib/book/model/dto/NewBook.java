package edu.kh.cooof.lib.book.model.dto;

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
public class NewBook {
	
	private int newBookNo;
	private String newTitle;
	private String authorLastName;
	private String authorFirstName;
	private String publisher;
	private int newBookCheck;
	private int memberNo;
	
}
