package edu.kh.cooof.lesson.common;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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

	// PDF 파일 테이블 생성 메서드
    public static void drawTable(
            PDDocument document,
            PDPage page,
            PDPageContentStream contentStream,
            PDType0Font font,
            String memberName,
            String instCategory,
            String instIntro) throws IOException {

        PDRectangle mediaBox = page.getMediaBox();
        float pageWidth = mediaBox.getWidth();

        // 테이블 초기 설정
        float margin = 50;
        float yStart = 700;
        float tableWidth = 400;
        float xCenter = (pageWidth - tableWidth) / 2; // 중앙 정렬
        float yPosition = yStart;
        float rowHeight = 40f;
        float[] colWidths = { 100, 300 };

        String[][] content = {
                { "지원자명", memberName },
                { "지원 분야", instCategory },
                { "자기 소개", instIntro }
        };

        // "지원서" 제목 추가
        contentStream.beginText();
        contentStream.setFont(font, 16);
        contentStream.newLineAtOffset(xCenter + tableWidth / 2 - 20, yStart + 50);
        contentStream.showText("지원서");
        contentStream.endText();

        // 테이블 그리기
        for (int i = 0; i < content.length; i++) {
            float nextY = yPosition - rowHeight;
            drawLine(contentStream, xCenter, yPosition, xCenter + tableWidth, yPosition);
            drawLine(contentStream, xCenter, nextY, xCenter + tableWidth, nextY);
            yPosition = nextY;
        }

        for (int i = 0; i <= colWidths.length; i++) {
            float nextX = xCenter;
            for (int j = 0; j < i; j++) {
                nextX += colWidths[j];
            }
            drawLine(contentStream, nextX, yStart, nextX, yPosition);
        }

        // 셀에 텍스트 추가
        float textx = xCenter + 5;
        float texty = yStart - 15;
        for (int i = 0; i < content.length; i++) {
            float nextTextX = textx;
            for (int j = 0; j < content[i].length; j++) {
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(nextTextX, texty);
                contentStream.showText(content[i][j]);
                contentStream.endText();
                nextTextX += colWidths[j];
            }
            texty -= rowHeight;
        }
    }

    // PDF 테이블 생성 메서드용 라인 생성기
    public static void drawLine(PDPageContentStream contentStream, float xStart, float yStart, float xEnd, float yEnd)
            throws IOException {
        contentStream.moveTo(xStart, yStart);
        contentStream.lineTo(xEnd, yEnd);
        contentStream.stroke();
    }

}
