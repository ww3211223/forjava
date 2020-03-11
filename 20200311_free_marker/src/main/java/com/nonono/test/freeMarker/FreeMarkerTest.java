package com.nonono.test.freeMarker;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class FreeMarkerTest {

    public void testSystemOut() {
        Map root = new HashMap();
        root.putIfAbsent("id", 123);
        root.putIfAbsent("name", "艾查恩");

        try {
            Template template = FreeMarkerConf.cfg.getTemplate("test.ftl");
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void testFileOut() {
        Map root = new HashMap();
        root.putIfAbsent("id", 456);
        root.putIfAbsent("name", "冯卡斯坦");
        String filePath = "E:\\Work2\\forjava\\20200311_free_marker\\src\\main\\resources\\template\\test.html";

        try {
            Template template = FreeMarkerConf.cfg.getTemplate("test.ftl");
            Writer out = new OutputStreamWriter(new FileOutputStream(filePath));
            template.process(root, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        String propPath = getClass().getClassLoader().getResource("application.properties").getPath();
        System.out.println("propPath:" + propPath);
        System.out.println("user.dir:" + System.getProperty("user.dir"));
    }

}
