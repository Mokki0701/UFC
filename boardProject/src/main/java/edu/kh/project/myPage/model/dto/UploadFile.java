package edu.kh.project.myPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Builder : 빌더 패턴을 이용해 객체 생성 및 초기화를 쉽게 진행
// -> 기본 생성자가 생성 안됨
//  -> MyBatis 조회 결과를 담을 때 필요한 객체 생성 실패
//    (MyBatis는 기본 생성자로 객체를 만들기 때문!)

@Getter
@Setter
@Builder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class UploadFile {

	private int fileNo;
	private String filePath;
	private String fileOriginalName;
	private String fileRename;
	private String fileUploadDate;
	private int memberNo;
	private String memberNickname;
	
}
