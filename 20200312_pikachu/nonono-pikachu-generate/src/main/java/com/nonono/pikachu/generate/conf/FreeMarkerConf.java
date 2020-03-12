package com.nonono.pikachu.generate.conf;


import freemarker.template.Configuration;

/**
 * freeMarker配置静态类
 */
public class FreeMarkerConf {

    /**
     * freeMarker配置
     */
    public static Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

    static {
        cfg.setClassForTemplateLoading(FreeMarkerConf.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
    }
}
