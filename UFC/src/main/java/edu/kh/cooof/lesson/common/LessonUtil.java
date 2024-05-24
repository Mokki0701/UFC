package edu.kh.cooof.lesson.common;

import org.springframework.stereotype.Service;

@Service
public class LessonUtil {

    public String classifyTimeOfDay(String lessonSchedule) {
        // lessonSchedule을 시작 시간과 종료 시간으로 분리
        String[] times = lessonSchedule.split(" ~ ");
        String startTime = times[0];

        // 시작 시간을 시간과 분으로 분리
        String[] timeParts = startTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);

        // '오전'과 '오후'를 분류
        if (hour < 12) {
            return "오전";
        } else {
            return "오후";
        }
    }
	
}
