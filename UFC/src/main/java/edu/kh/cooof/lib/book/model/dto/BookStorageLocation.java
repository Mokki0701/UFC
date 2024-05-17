package edu.kh.cooof.lib.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookStorageLocation {

	private int storageNo;
	private int floor;
	private String storageName;
	
	// bookList에서 전체를 보여줄 때 사용할 필드값들
	private int catNo;
	private String catName;
	
}
