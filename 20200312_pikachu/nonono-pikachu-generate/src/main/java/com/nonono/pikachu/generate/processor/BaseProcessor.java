package com.nonono.pikachu.generate.processor;

import com.nonono.pikachu.generate.model.GenerateConfigInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * freemarker 模板处理器
 */
public abstract class BaseProcessor {

    /**
     * 模板数据
     */
    public Map<String, Object> root;

    protected GenerateConfigInfo projectConfig;

    public BaseProcessor() {
        root = new HashMap<>();
    }

    /**
     * 获取输出文件路径
     *
     * @return
     */
    public abstract String getOutFilePath();

    /**
     * 获取模板名称
     *
     * @return
     */
    public abstract String getFtlName();

    /**
     * 加载资源
     */
    public abstract void loadSource();

    /**
     * 根目录地址
     */
    private String rootPath;

    /**
     * 设置配置信息
     *
     * @param projectConfig
     */
    public void setGenerateConfig(GenerateConfigInfo projectConfig) {
        this.projectConfig = projectConfig;
        root.put("generateConfig", projectConfig);
    }

    /**
     * 获取项目根目录地址
     *
     * @return
     */
    public String getRootPath() {
        if (rootPath != null) {
            return rootPath;
        }

        rootPath = projectConfig.getGenerateClass().getClassLoader().getResource(projectConfig.getResourceFile()).getPath();
        rootPath = rootPath.substring(0, rootPath.indexOf(projectConfig.getCurrentArtifactId()));
        return rootPath;
    }
}
