package edu.kh.cooof.lesson.common.pdf;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ThymeleafParser {

    public static String parseHtmlFileToString(String fileName, Map<String, Object> variableMap) {
        // 타임리프 resolver 설정을 잡아준다.
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // templates 경로 아래에 있는 파일을 읽는다
        templateResolver.setSuffix(".html"); // .html로 끝나는 파일을 읽는다
        templateResolver.setTemplateMode(TemplateMode.HTML); // 템플릿은 html 형식이다

        // 스프링 template 엔진을 thymeleafResolver를 사용하도록 설정
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // 템플릿 엔진에서 사용될 변수를 넣어준다.
        Context context = new Context();
        context.setVariables(variableMap);

        // 지정한 html 파일과 context를 읽어 String으로 반환한다.
        return templateEngine.process(fileName, context);
    }

    public static String generateFromHtml(String filePath, String fileName, String html) throws IOException {
        // pdf파일을 저장할 위치
        String savedFilePath = filePath + "/" + fileName + ".pdf";

        try (FileOutputStream outputStream = new FileOutputStream(savedFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            
            
            // 폰트 리졸버에 한글 폰트 추가
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(
                new ClassPathResource("/static/font/NotoSansKR-Bold.ttf").getURL().toString(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
            );
            
            
            
            
            
            

            // 커스텀한 replacedElementFactory를 사용한다
            renderer.getSharedContext().setReplacedElementFactory(
                new ImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory())
            );

            renderer.setDocumentFromString(html); // document형식으로 바꿔주기 위해 html의 변환된 string을 인수로 넣는다
            renderer.layout(); // pdf 모양을 잡아주는 메서드들이 실행된다(퍼사드 패턴)
            renderer.createPDF(outputStream);

            // pdf 파일이 저장된 경로를 알려주기 위해 리턴
            return savedFilePath;
        }
    }
}
