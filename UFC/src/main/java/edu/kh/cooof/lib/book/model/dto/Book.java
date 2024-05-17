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
public class Book {

	private int bookNo;
	private String bookTitle;
	private String authorLastName;
	private String authorFirstName;
	private String bookPublisher;
	private String bookYear;
	private String bookImg;
	private String bookTmi;
	private int availableCount;
	private String storageDetail;
	private int lrFlag;
	private int catNo;
	private int bookLikeNum;
	
	
}
