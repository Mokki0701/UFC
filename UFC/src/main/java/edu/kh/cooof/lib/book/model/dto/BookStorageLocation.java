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
	
	
}
