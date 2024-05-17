package edu.kh.cooof.lib.book.model.dto;

public class LibPagination {

	private int currentPage;
	private int listCount;
	
	private int limit;
	private int pageSize = 10;
	
	private int maxPage;
	private int startPage;
	private int endPage;
	
	private int prevPage;
	private int nextPage;
	
	public LibPagination(int currentPage, int listCount, int limit) {
		super();
		this.currentPage = currentPage;
		this.listCount   = listCount;
		this.limit		 = limit;
		
		calculate();
		
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public int getListCount() {
		return listCount;
	}
	public int getLimit() {
		return limit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public String toString() {
		return "Pagination [currentPage=" + currentPage + ", listCount=" + listCount + ", limit=" + limit
				+ ", pageSize=" + pageSize + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + "]";
	}
	
private void calculate() {
		
		maxPage = (int)Math.ceil((double)listCount/limit); // 마지막 페이지
		
		int re = currentPage / pageSize;
		
		startPage = re*10 + 1; // 각 목록의 시작 번호
		
		endPage = startPage + 8; // 목록의 마지막 페이지 번호
		if(startPage > endPage) endPage = startPage;
		
		if(endPage > maxPage) endPage = maxPage;
		
		if(currentPage < pageSize) prevPage = 1;
		else prevPage = startPage - 1;
		
		if(endPage == maxPage) nextPage = endPage;
		else nextPage = endPage + 1;
		
	}
	
	
	
	
}
