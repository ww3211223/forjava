package com.nonono.test;

import com.nonono.test.aop.*;
import com.nonono.test.ioc.Product;
import com.nonono.test.ioc.SimpleIOC;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        testAOP();
    }

    public static void testIOC() {
        try {
            String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
            SimpleIOC bf = new SimpleIOC(location);

            Product product = (Product) bf.getBean("product");
            System.out.println("name:" + product.getName());
            System.out.println("title:" + product.getTitle());
            System.out.println("memo:" + product.getMemo());
            System.out.println("madeContry:" + product.getProductSource().getMadeContry());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testAOP() {
        // 创建一个MethodInvocation 实现类
        MethodInvocation logTask = () -> System.out.println("log task start.");
        MethodInvocation afterTask = () -> System.out.println("log task end.");
        HelloService helloServiceImpl = new HelloServiceImpl();

        //创建一个Advice
        Advice beforeAdvice = new BeforeAdvice(helloServiceImpl, logTask);

        //为目标类对象生成代理
        HelloService helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloServiceImpl, beforeAdvice);
        Advice afterAdvice = new AfterAdvice(helloServiceImplProxy, afterTask);
        helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloServiceImpl, afterAdvice);
        helloServiceImplProxy.work();
    }
}
