package edu.kh.cooof.lesson.list.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

@Mapper
public interface LessonListMapper {

	/** 모든 수업의 개수를 조회 (마감 여부 관계 없이)
	 * @return
	 */
	int getLessonCount();

	/** 지정된 페이지의 수업 목록 조회해오기
	 * @param rowBounds
	 * @return
	 */
	List<Lesson> selectLessonList(RowBounds rowBounds);

}
