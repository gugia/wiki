package com.personal.wiki.collection;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map与Bean的引用关系例子
 *
 * @author L.X <gugia@qq.com>
 */
public class MapItem {

    private final static Map<String, ExampleBean> MAP = new ConcurrentHashMap<>(128);
    private final static Map<String, Integer> INT = new ConcurrentHashMap<>(128);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ExampleBean bean = new ExampleBean("example" + String.valueOf(i + 1));
            bean.setHttpStatus(HttpStatus.valueOf(400 + i));
            MAP.put(bean.getName(), bean);
        }
        printMap(MAP);
        ExampleBean exmaple3 = MAP.get("example3");
        exmaple3.setHttpStatus(HttpStatus.OK);
        printMap(MAP);

        for (int i = 0; i < 10; i++) {
            INT.put("integer" + String.valueOf(i + 1), i);
        }
        printMap(INT);
        Integer value = INT.get("integer3");
        value = 5555;
        printMap(INT);
    }

    private static void printMap(Map<String, ?> map) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.printf("key: %s, value: %s", entry.getKey(), entry.getValue().toString());
            System.out.println();
        }
    }
}

class ExampleBean {

    private String name;

    private HttpStatus httpStatus;

    public ExampleBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ExampleBean(" + name + ", HttpStatus." + httpStatus.toString() + ")";
    }
}
