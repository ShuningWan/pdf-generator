package xin.wanwork.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

/**
 * @author Oct.
 */
public class TemplateFactory {

    private static final Configuration CONFIGURATION;

    static {
        CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setClassLoaderForTemplateLoading(TemplateFactory.class.getClassLoader(), "templates");
    }

    /**
     * 根据名称获取模板
     *
     * @param name 模板名称
     * @return Template
     */
    @SneakyThrows(Exception.class)
    public static Template getTemplate(String name) {
        return CONFIGURATION.getTemplate(name);
    }

}