//package edu.kh.cooof.lesson.common.pdf;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.Map;
//
//@Service
//public class PdfService {
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
//    private static final String PDF_STORAGE_PATH = "C:/mokkie/lesson/instReg/";
//
//    public String generatePdf(String templateName, Map<String, Object> data) throws Exception {
//        Context context = new Context();
//        context.setVariables(data);
//
//        // HTML 템플릿 처리
//        String htmlContent = templateEngine.process(templateName, context);
//
//        // 생성된 HTML 콘텐츠 디버깅
//        System.out.println("Generated HTML Content: ");
//        System.out.println(htmlContent);
//
//        // PDF 생성
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
//        document.open();
//
//        ITextRenderer renderer = new ITextRenderer();
//
//        // 리소스 경로 설정 - 클래스패스를 이용한 상대 경로
//        String baseUri = this.getClass().getResource("/static/").toExternalForm();
//        renderer.setDocumentFromString(htmlContent, baseUri);
//        renderer.layout();
//
//        // PDF 페이지가 없는지 확인
//        if (renderer.getRootBox().getLayer().getPages().size() == 0) {
//            throw new IllegalArgumentException("The generated document has no pages.");
//        }
//
//        renderer.createPDF(byteArrayOutputStream);
//
//        document.close();
//        writer.close();
//
//        // PDF 파일 저장
//        String pdfFileName = PDF_STORAGE_PATH + "generated_info.pdf";
//        try (OutputStream outputStream = new FileOutputStream(new File(pdfFileName))) {
//            outputStream.write(byteArrayOutputStream.toByteArray());
//        }
//
//        return pdfFileName;
//    }
//
//    public String saveUploadedPdf(MultipartFile pdfFile, int i) throws Exception {
//        String pdfFileName = PDF_STORAGE_PATH + i + "_resume.pdf";
//        File dest = new File(pdfFileName);
//        pdfFile.transferTo(dest);
//        return pdfFileName;
//    }
//}
