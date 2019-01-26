package com.nonono.test;

import com.nonono.test.fortest.TestBean;
import com.nonono.test.fortest.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("dev")
public class DemoBeanIntegrationTests {

    @Autowired
    private TestBean testBean;

    @Test
    public void prodBeanShouldInject() {
        String content = testBean.getContent();
        System.out.println("content: " + content);
        Assert.assertTrue(!StringUtils.isEmpty(content));
    }
}
