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
public class LoanBook {
	
	private int loanBookNo;
	private String bookStart;
	private String bookEnd;
	private int bookExtend;
	private int bookReturn;
	private int loanBookStatus;
	
	private int memberNo;
	private int bookNo;
	
	private String bookTitle;
	private String memberLastName;
	private String memberFirstName;
	
}
