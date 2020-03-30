package com.nonono.test;

import com.google.common.collect.Maps;
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
import com.nonono.test.delayQueue.TestDelayQueue;
import com.nonono.test.el.ElConfig;
import com.nonono.test.event.DemoPublisher;
import com.nonono.test.event.EventConfig;
import com.nonono.test.findBean.DiConfig;
import com.nonono.test.findBean.UseFunctionService;
import com.nonono.test.generic.TestGeneric;
import com.nonono.test.prepost.BeanWayService;
import com.nonono.test.prepost.JSR250WayService;
import com.nonono.test.prepost.PrePostConfig;
import com.nonono.test.scope.DemoPrototypeService;
import com.nonono.test.scope.DemoSingletonService;
import com.nonono.test.scope.ScopeConfig;
import com.nonono.test.taskexecutor.AsyncTaskService;
import com.nonono.test.taskexecutor.TaskExecutorConfig;
import com.nonono.test.taskscheduler.TaskSchedulerConfig;
import com.nonono.test.threadTest.WaitNotify;
import guavaTest.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws NoSuchFieldException {
        //testAop();
        //testGenericType();
        //testTreeMap();
        //testDelayQueue();
        //testGuavaCache();
        //testWaitNotify();
        //testGuavaCollection();
        //testGuavaPredicateAndFilter();
        //testGuavaUtil();
        //testEventBus();
        //testGuavaReflection();
        testHashing();
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

    /**
     * 测试获取泛型类型
     *
     * @throws NoSuchFieldException
     */
    public static void testGenericType() throws NoSuchFieldException {
        TestGeneric<String> test = new TestGeneric<>();
        test.setContext("abc");

        Type type = test.getClass().getDeclaredField("context").getGenericType();
        System.out.println("testGenericType：" + type);
    }

    /**
     * 测试TreeMap
     */
    public static void testTreeMap() {
        TreeMap<Integer, String> treeMap = Maps.newTreeMap();
        treeMap.put(654, "test");
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int key = random.nextInt(1000);
            treeMap.put(key, "sort:" + i);
        }
        for (Map.Entry<Integer, String> item : treeMap.entrySet()) {
            System.out.println("key:" + item.getKey() + ",value:" + item.getValue());
        }

        System.out.println("testKey value:" + treeMap.get(654));
        System.out.println(treeMap);
    }

    public static void testDelayQueue() {
        TestDelayQueue delayQueue = new TestDelayQueue();
        delayQueue.test();
    }

    public static void testGuavaCache() {
        TestGuavaCache test = new TestGuavaCache();
        test.test();
    }

    public static void testWaitNotify() {
        WaitNotify waitNotify = new WaitNotify();
        waitNotify.test();
    }

    public static void testGuavaCollection() {
        GuavaCollectionTest guavaCollectionTest = new GuavaCollectionTest();
        guavaCollectionTest.test();
    }

    public static void testGuavaPredicateAndFilter() {
        GuavaPredicateAndFilterTest filterTest = new GuavaPredicateAndFilterTest();
        filterTest.test();
    }

    public static void testGuavaUtil() {
        GuavaUtilTest test = new GuavaUtilTest();
        test.test();
    }

    public static void testEventBus() {
        EventBusTest test = new EventBusTest();
        test.test();
    }

    public static void testGuavaReflection() {
        GuavaReflectionTest reflectionTest = new GuavaReflectionTest();
        reflectionTest.test();
    }

    public static void testHashing() {
        GuavaHashingTest hashingTest = new GuavaHashingTest();
        hashingTest.test();
    }
}
