package edu.kh.project.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Board {
	
	// BOARD 테이블 컬럼
	private int 	boardNo;
	private String 	boardTitle; 
	private String 	boardContent;
	private String 	boardWriteDate;
	private String 	boardUpdateDate;
	private int		readCount;
	private String  boardDelFl;
	private int 	memberNo;
	private int 	boardCode;
	
	// MEMBER 테이블 조인
	private String 	memberNickname;
	
	// 목록 조회 시 상관 서브 쿼리 결과
	private int		commentCount;
	private int		likeCount;
	
	
	
	// 게시글 작성자 프로필 이미지
	private String profileImg;
	
	// 게시글 목록 썸네일 이미지
	private String thumbnail;
	
	// 특정 게시글 이미지 목록
	private List<BoardImg> imageList;
	
	// 특정 게시글에 작성된 댓글 목록
	private List<Comment> commentList;
	
	// 좋아요 여부 확인
	private int likeCheck;
	
	
	

}
