package edu.kh.cooof.lesson.common.pdf;

import com.lowagie.text.Image;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * resources 경로에 있는 이미지 파일을 PDF로 변환하기 위한 factory
 */
public class ImageReplacedElementFactory implements ReplacedElementFactory {

    private final ReplacedElementFactory replacedElementFactory;

    public ImageReplacedElementFactory(ReplacedElementFactory replacedElementFactory) {
        this.replacedElementFactory = replacedElementFactory;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
                                                 UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String nodeName = element.getNodeName();
        String srcPath = element.getAttribute("src");

        if ("img".equals(nodeName) && srcPath.startsWith("/image")) {
            try {
                Path path = Path.of(new ClassPathResource("static" + srcPath).getURI());
                byte[] imageBytes = Files.readAllBytes(path);
                Image image = Image.getInstance(imageBytes);
                ITextFSImage fsImage = new ITextFSImage(image);

                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                }

                return new ITextImageElement(fsImage);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return replacedElementFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void remove(Element e) {
        replacedElementFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        replacedElementFactory.setFormSubmissionListener(listener);
    }

    @Override
    public void reset() {
        replacedElementFactory.reset();
    }
}
