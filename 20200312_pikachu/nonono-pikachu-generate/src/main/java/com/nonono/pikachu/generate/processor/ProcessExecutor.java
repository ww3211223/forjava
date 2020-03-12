package com.nonono.pikachu.generate.processor;

import com.nonono.pikachu.generate.conf.FreeMarkerConf;
import com.nonono.pikachu.generate.model.GenerateConfigInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 处理器执行器
 */
public class ProcessExecutor {

    /**
     * 执行模板生成
     *
     * @param processor
     */
    public static void generateAsFile(GenerateConfigInfo generateConfigInfo, BaseProcessor processor) throws IOException, TemplateException {
        if (processor == null) {
            return;
        }

        processor.setGenerateConfig(generateConfigInfo);
        processor.loadSource();
        try {
            Template template = FreeMarkerConf.cfg.getTemplate(processor.getFtlName());
            Writer out = new OutputStreamWriter(new FileOutputStream(processor.getOutFilePath()));
            template.process(processor.root, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            throw e;
        }
    }

    /**
     * 执行模板生成
     *
     * @param processor
     * @param processors
     */
    public static void generateAsFile(GenerateConfigInfo generateConfigInfo, BaseProcessor processor, BaseProcessor... processors) throws IOException, TemplateException {
        generateAsFile(generateConfigInfo, processor);
        for (BaseProcessor item : processors) {
            generateAsFile(generateConfigInfo, item);
        }
    }


}
