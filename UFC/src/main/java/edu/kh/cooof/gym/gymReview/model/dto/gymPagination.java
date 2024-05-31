package edu.kh.cooof.gym.gymReview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class gymPagination {

	private int currentPage;		// 현재 페이지 번호
	private int listCount;			// 전체 게시글 수
	
	private int limit = 10;			// 한 페이지 목록에 보여지는 게시글 수
	private int pageSize = 10;		// 보여질 페이지 번호 개수
	
	private int maxPage;			// 마지막 페이지 번호
	private int startPage;			// 보여지는 맨 앞 페이지 번호 
	private int endPage;			// 보여지는 맨 뒤 페이지 번호
	
	private int prevPage;			// 이전 페이지 모음의 마지막 번호 
	private int nextPage;			// 다음 페이지 모음의 시작 번호 
	
	
	
	/**
	 * 페이징 처리에 필요한 값을 계산해서
	 * 필드에 대입하는 메서드
	 * (maxPage, startPage, endPage,  prevPage, nextPage)
	 */
	public void calculate() {
		
		
		// maxPage : 최대 페이지 == 마지막 페이지 == 총 페이지 수
		
		// 한 페이지에 게시글이 10개씩 보여질 경우
		//  게시글 수 : 95개 -> 10page
		//  게시글 수 :100개 -> 10page
		//  게시글 수 :101개 -> 11page
		
		maxPage = (int)Math.ceil( (double)listCount / limit );
		
		
		// startPage : 페이지 번호 목록의 시작 번호
		
		// 페이지 번호 목록이 10개(pageSize) 씩 보여질 경우
		
		// 현재 페이지가  1 ~ 10 :  1 page
		// 현재 페이지가 11 ~ 10 : 11 page
		
		startPage = (currentPage - 1) / pageSize * pageSize + 1;
		
		
		// endPage : 페이지 번호 목록의 끝 번호
		
		// 현재 페이지가   1 ~ 10 :  10 page
		// 현재 페이지가  11 ~ 20 :  20 page
		// 현재 페이지가  21 ~ 30 :  30 page
		endPage = pageSize -1 + startPage;
		
		// 페이지 끝 번호가 최대 페이지를 초과한 경우
		if(endPage > maxPage) endPage = maxPage;
		
		
		// prevPage : "<" 클릭 시 이동할 페이지 번호
		//            (이전 페이지 번호 목록 중 끝 번호)
		
		
		// 더 이상 뒤로갈 페이지가 없을 경우
		if(currentPage < pageSize) {
			prevPage = 1;
			
		} else {
			prevPage = startPage - 1;
		}
		
		// nextPage : ">" 클릭 시 이동할 페이지 번호
		//            (다음 페이지 번호 목록 중 끝 번호)
		
		// 더 이상 넘어갈 페이지가 없을 경우
		if(endPage == maxPage) {
			nextPage = maxPage;
		} else {
			nextPage = endPage + 1;
		}
	}
	
	public gymPagination(int currentPage, int listCount) {
	    this.currentPage = currentPage;
	    this.listCount = listCount;
	    this.calculate();
	}
}
