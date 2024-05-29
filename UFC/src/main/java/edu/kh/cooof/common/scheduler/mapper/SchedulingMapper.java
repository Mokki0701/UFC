package edu.kh.cooof.common.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulingMapper {

	List<String> selectLessonImageNames();

}
