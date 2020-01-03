package com.nonono.test;

import com.nonono.test.Sort.QuickSort;
import com.nonono.test.Sort.ShellSort;
import com.nonono.test.annotation.DemoConfig;
import com.nonono.test.annotation.DemoService;
import com.nonono.test.aop.AopConfig;
import com.nonono.test.aop.DemoAnnotationService;
import com.nonono.test.aop.DemoMethodService;
import com.nonono.test.aware.AwareConfig;
import com.nonono.test.aware.AwareService;
import com.nonono.test.conditional.ConditionConfig;
import com.nonono.test.conditional.ListService;
import com.nonono.test.el.ElConfig;
import com.nonono.test.event.DemoPublisher;
import com.nonono.test.event.EventConfig;
import com.nonono.test.findBean.DiConfig;
import com.nonono.test.findBean.UseFunctionService;
import com.nonono.test.prepost.BeanWayService;
import com.nonono.test.prepost.JSR250WayService;
import com.nonono.test.prepost.PrePostConfig;
import com.nonono.test.scope.DemoPrototypeService;
import com.nonono.test.scope.DemoSingletonService;
import com.nonono.test.scope.ScopeConfig;
import com.nonono.test.taskexecutor.AsyncTaskService;
import com.nonono.test.taskexecutor.TaskExecutorConfig;
import com.nonono.test.taskscheduler.TaskSchedulerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //testAop();
        System.out.println(String.format("%s|%d", 1000L, 9999L));
        System.out.println(MessageFormat.format("{0}|{1}", 1000L, 999L));
    }

    public static void testFindBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);
        UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
        System.out.println(useFunctionService.sayHello("Word."));
        context.close();
    }

    public static void testAop() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);
        DemoMethodService demoMethodService = context.getBean(DemoMethodService.class);

        demoAnnotationService.add();
        demoMethodService.add();
        demoMethodService.set(10);
        context.close();
    }

    public static void testScope() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
        DemoSingletonService s1 = context.getBean(DemoSingletonService.class);
        DemoSingletonService s2 = context.getBean(DemoSingletonService.class);

        DemoPrototypeService p1 = context.getBean(DemoPrototypeService.class);
        DemoPrototypeService p2 = context.getBean(DemoPrototypeService.class);

        System.out.println("s1与s2是否相等：" + s1.equals(s2));
        System.out.println("p1与p2是否相等：" + p1.equals(p2));
        context.close();
    }

    public static void testEl() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
        ElConfig resourceService = context.getBean(ElConfig.class);
        resourceService.outputResource();
        context.close();
    }

    public static void testPrepost() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrePostConfig.class);
        BeanWayService beanWayService = context.getBean(BeanWayService.class);
        JSR250WayService jsr250WayService = context.getBean(JSR250WayService.class);
        context.close();
    }

    public static void testEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher demoPublisher = context.getBean(DemoPublisher.class);
        demoPublisher.publish("hello world.");
        context.close();
    }

    public static void testAware() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
        AwareService service = context.getBean(AwareService.class);
        service.outputResult();

        context.close();
    }

    public static void testTaskExecutor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
        for (int i = 0; i < 10; i++) {
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
        context.close();
    }

    public static void testTaskScheduler() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskSchedulerConfig.class);
    }

    public static void testConditional() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        ListService bean = context.getBean(ListService.class);
        System.out.println("当前系统下的列表命令为：" + bean.showListCmd());
    }

    public static void testAnnotation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
        DemoService demoService = context.getBean(DemoService.class);
        demoService.outputResult();
        context.close();
    }

    public static void testShellSort() {
        ShellSort shellSort = new ShellSort();
        shellSort.test();
    }

    public static void testQuickSort() {
        QuickSort quickSort = new QuickSort();
        quickSort.test();
    }

    public static void testGC() {
        byte[] allocation1, allocation2;
        allocation1 = new byte[60900 * 1024];
        allocation2 = new byte[900 * 1024];
    }
}
