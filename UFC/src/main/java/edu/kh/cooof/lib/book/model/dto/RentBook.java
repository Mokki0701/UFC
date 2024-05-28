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
public class RentBook {

	private int rentBookNo;
	private int memberNo;
	private int bookNo;
	private String bookReserve;
	private int rentBookCheck;
	
	// 조회할 요소
	private String bookTitle;
	private String authorLastName;
	private String authorFirstName;
	private String bookPublisher;
	private String bookYear;
	private String bookImg;
	private String bookTmi;
	
	// 테이블에 없는 조회 요소
	private int totalCount;
	
}
