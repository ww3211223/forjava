package guavaTest;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import java.io.IOException;
import java.util.List;

/**
 * guava
 */
public class GuavaReflectionTest {

    public void test() {
        testClassPath();
    }

    /**
     * 测试查找ClassPath
     */
    private void testClassPath() {
        List<String> classNames = Lists.newArrayList();
        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses("guavaTest")) {
                classNames.add(classInfo.getSimpleName());
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(classInfo.getName()).getBeanDefinition();
                if(beanDefinition == null) {
                    System.out.println("beanDefinition is null.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("classNames: " + Joiner.on(",").skipNulls().join(classNames));
    }
}
