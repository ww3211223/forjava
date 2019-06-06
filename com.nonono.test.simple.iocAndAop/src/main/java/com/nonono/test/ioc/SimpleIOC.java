package com.nonono.test.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleIOC {
    private Map<String, Object> beanMap = new HashMap<>();

    public SimpleIOC(String location) throws Exception {
        loadBeans(location);
    }

    /**
     * 获取Bean
     *
     * @param name
     * @return
     */
    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException(String.format("this bean name[%s] is notfind.", name));
        }

        return bean;
    }

    /**
     * 加载Bean
     *
     * @param location
     * @throws Exception
     */
    public void loadBeans(String location) throws Exception {
        //加载xml配置文件
        InputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        //遍历beans节点
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String className = element.getAttribute("class");

                //加载bean
                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }

                //创建bean对象
                Object bean = beanClass.newInstance();

                //遍历属性
                NodeList propertys = element.getElementsByTagName("property");
                for (int j = 0; j < propertys.getLength(); j++) {
                    Node propertyNode = propertys.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");
                        String ref = propertyElement.getAttribute("ref");

                        //利用反射将bean对于字段访问权限设置为可访问
                        Field declaredField = bean.getClass().getDeclaredField(name);
                        declaredField.setAccessible(true);

                        //将属性值填充至相应字段中
                        if (value != null && value.length() > 0) {
                            declaredField.set(bean, value);
                        } else if (ref != null && ref.length() > 0) {
                            declaredField.set(bean, getBean(ref));
                        } else {
                            throw new IllegalArgumentException(String.format("propertry: %s value or ref is null.", name));
                        }
                    }
                }

                registerBean(id, bean);
            }
        }
    }

    /**
     * 注册至容器
     *
     * @param id
     * @param bean
     */
    public void registerBean(String id, Object bean) {
        beanMap.putIfAbsent(id, bean);
    }
}
