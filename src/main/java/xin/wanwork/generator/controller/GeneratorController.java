package xin.wanwork.generator.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.wanwork.generator.utils.GeneratorUtil;
import xin.wanwork.generator.utils.HeaderFooterHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ShuningWan
 */
@Slf4j
@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class GeneratorController {

    private final Executor executor;

    /**
     * 预览报告
     */
    @GetMapping("preview/{uuid}")
    public void preview(@PathVariable(name = "uuid") String uuid, HttpServletResponse response) {
        export(uuid, response, true);
    }

    /**
     * 导出报告
     */
    @GetMapping("export/{uuid}")
    public void export(@PathVariable(name = "uuid") String uuid, HttpServletResponse response) {
        export(uuid, response, false);
    }

    /**
     * PDF 预览 | 导出 报告
     *
     * @param template 模板名称
     * @param response 响应流
     * @param preview  预览模式(preview:true)  | 下载模式(preview:false)
     */
    @SneakyThrows(Exception.class)
    private void export(String template, HttpServletResponse response, boolean preview) {
        TimeInterval timer = DateUtil.timer();
        response.setContentType("application/pdf;charset=utf-8");
        if (!preview) {
            String name = URLEncoder.encode(IdUtil.fastSimpleUUID() + ".pdf", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        }
        GeneratorUtil.export(template, getData(), HeaderFooterHandler.class, response.getOutputStream());
        log.debug("报告导出: 耗时: {}ms", timer.intervalMs());
    }

    /**
     * 模拟数据
     *
     * @return map
     */
    private Map<String, Object> getData() {
        Map<String, Object> data = MapUtil.newHashMap();
        data.put("chinese", "中文汉字");
        data.put("english", "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]");
        data.put("symbol", "[，。、；‘【】{}~！@#￥%……&*（）——+]");
        data.put("number", "[0123456789]");
        return data;
    }

}
