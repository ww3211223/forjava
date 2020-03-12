package com.nonono.pikachu.generate.processor;

import com.nonono.pikachu.generate.model.GenerateConfigInfo;

/**
 * 测试处理器
 */
public class TestProcessor extends BaseProcessor {

    private final String outFilePath = "/src/main/resources/test.html";
    private final String ftlName = "test.ftl";

    @Override
    public String getOutFilePath() {
        return getRootPath() + projectConfig.getCurrentArtifactId() + "/" + outFilePath;
    }

    @Override
    public String getFtlName() {
        return ftlName;
    }

    @Override
    public void loadSource() {
        root.putIfAbsent("id", 123);
        root.putIfAbsent("name", "艾查恩");
    }
}
