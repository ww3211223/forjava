package com.nonono.test.pikachu;

import com.nonono.pikachu.generate.model.GenerateConfigInfo;
import com.nonono.pikachu.generate.processor.ProcessExecutor;
import com.nonono.pikachu.generate.processor.TestProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 测试Job
 */
@Component
public class TestJob implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("testJob is start.");

        GenerateConfigInfo generateConfigInfo = GenerateConfigInfo.builder()
                .packageName("com.nonono.test.pikachu")
                .currentArtifactId("20200312-pikachu-test")
                .generateClass(TestJob.class)
                .resourceFile("application.properties")
                .build();
        TestProcessor testProcessor = new TestProcessor();
        ProcessExecutor.generateAsFile(generateConfigInfo, testProcessor);

        System.out.println("testJob is end.");
    }
}
