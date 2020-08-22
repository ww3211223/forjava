package com.nonono.test.ame.core.message;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 命令指令工厂类
 */
public class CmdDirectiveFactory {

    private static Map<Integer, CmdDirective> cache = Maps.newHashMap();

    public CmdDirectiveFactory() {
    }

    /**
     * 注册命令指令
     *
     * @param directive
     */
    public static void register(CmdDirective directive) {
        cache.put(directive.getCode(), directive);
    }

    /**
     * 获取命令指令
     *
     * @param directiveVal
     * @return
     */
    public static CmdDirective get(int directiveVal) {
        return (CmdDirective) cache.get(directiveVal);
    }
}
