package xin.wanwork.generator.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * @author Oct.
 */
@Slf4j
@NoArgsConstructor
public class HeaderFooterHandler implements IEventHandler {

    @Override
    public void handleEvent(final Event event) {
        try {
            final PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            final PdfDocument pdfDoc = docEvent.getDocument();
            final Document doc = new Document(pdfDoc);
            final PdfPage page = docEvent.getPage();
            final int pageNumber = pdfDoc.getPageNumber(page);
            final Rectangle pageSize = page.getPageSize();
            final float width = pageSize.getWidth();
            final float height = pageSize.getHeight();
            final PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            final Color lineColor = new DeviceRgb(57, 123, 198);
            pdfCanvas.setLineWidth(1.5f).setStrokeColor(lineColor);
            final float tableWidth = width - doc.getRightMargin() - doc.getLeftMargin();

            //页眉
            URL resource = ResourceUtil.getResource("files/logo.png");
            Image image = new Image(ImageDataFactory.create(resource));
            image.scaleToFit(134.04f, 34.29f);
            final Div logo = new Div();
            logo.setBorder(Border.NO_BORDER);
            logo.add(image);
            logo.setVerticalAlignment(VerticalAlignment.BOTTOM);
            logo.setTextAlignment(TextAlignment.CENTER);
            logo.setFixedPosition(width / 2 - 67.02f, height - 22.67f - 34.29f, tableWidth);
            doc.add(logo);

            // 页脚
            final Paragraph footer = new Paragraph();
            footer.setHeight(12f);
            footer.setFontColor(new DeviceRgb(166, 166, 166));
            footer.setFontSize(9f);
            footer.setTextAlignment(TextAlignment.CENTER);
            footer.setVerticalAlignment(VerticalAlignment.BOTTOM);
            footer.add("© Wanwork – All rights reserved. Reproduction, duplication and publication not permitted.");
            footer.setFixedPosition(doc.getLeftMargin(), doc.getBottomMargin() - 12f, tableWidth);
            doc.add(footer);
        } catch (final Exception e) {
            log.error("添加页眉页脚失败", e);
            throw new RuntimeException("添加页眉页脚失败");
        }
    }


}