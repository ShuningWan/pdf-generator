package xin.wanwork.generator.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.ReflectUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * @author Oct.
 */
@Slf4j
public final class GeneratorUtil {

    /**
     * 生成PDF到输出流中
     *
     * @param template ftl模板名称(默认在template下)
     * @param data     输入到FTL中的数据
     * @param event    事件监听
     * @param output   OutputStream
     * @return output
     */
    @SneakyThrows(Exception.class)
    public static OutputStream export(String template, Object data, Class<? extends IEventHandler> event, OutputStream output) {
        TimeInterval timer = DateUtil.timer();
        timer.start("parse");
        String html = getContent(template, data);
        log.debug("一阶段: 解析模板: 耗时{}ms", timer.intervalMs("parse"));
        timer.start("converter");
        ConverterProperties properties = buildConvertProperties();
        log.debug("二阶段: 构建转换器: 耗时{}ms", timer.intervalMs("converter"));
        timer.start("build");
        try (final OutputStream os = output;
             final PdfWriter pdfWriter = new PdfWriter(os);
             final PdfDocument document = new PdfDocument(pdfWriter);
        ) {
            document.setDefaultPageSize(PageSize.A4);
            if (event != null) {
                document.addEventHandler(PdfDocumentEvent.END_PAGE, ReflectUtil.newInstance(event));
            }
            try (final Document doc = HtmlConverter.convertToDocument(html, document, properties)) {
                //
            }
        } catch (Exception ex) {
            log.error("生成PDF 失败", ex);
            throw new RuntimeException("生成PDF 失败".concat(ex.getMessage()));
        }
        log.debug("三阶段: 报告构建: 耗时{}ms", timer.intervalMs("build"));
        return output;
    }

    /**
     * 获取模板并填充数据
     *
     * @param template ftl模板名称(默认在template下)
     * @param data     数据集
     * @return html
     */
    @SneakyThrows(Exception.class)
    public static String getContent(String template, Object data) {
        StringWriter writer = new StringWriter();
        TemplateFactory.getTemplate(template).process(data, writer);
        writer.flush();
        return writer.toString();
    }

    /**
     * 构建 ConverterProperties
     *
     * @return ConverterProperties
     */
    private static ConverterProperties buildConvertProperties() {
        ConverterProperties properties = new ConverterProperties();
        try {
            properties.setBaseUri(ResourceUtils.getFile("templates").getAbsolutePath());
            properties.setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
            FontProvider fontProvider = new FontProvider();
            fontProvider.addDirectory(System.getProperty("user.home").concat(File.separator).concat("fonts"));
            properties.setFontProvider(fontProvider);
            return properties;
        } catch (Exception e) {
            log.error("build convert properties error", e);
            throw new RuntimeException("build convert properties error");
        }
    }

}
