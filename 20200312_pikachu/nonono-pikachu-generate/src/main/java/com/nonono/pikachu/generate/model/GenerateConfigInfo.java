package com.nonono.pikachu.generate.model;

import lombok.*;

/**
 * 通用配置信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateConfigInfo {

    /**
     * 当前应用名称
     */
    private String currentArtifactId;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 生成类对象
     */
    private Class<?> generateClass;

    /**
     * 资源名称
     */
    private String resourceFile;
}
