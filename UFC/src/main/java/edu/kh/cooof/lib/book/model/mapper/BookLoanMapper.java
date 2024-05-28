package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.lib.book.model.dto.LoanBook;

@Mapper
public interface BookLoanMapper {

	int getListCount();

	List<LoanBook> selectList(RowBounds rowBounds);

}
