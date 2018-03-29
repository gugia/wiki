package com.personal.wiki.spring.lifecycle;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Spring - 容器的生命周期，多例
 *
 * @author L.X <gugia@qq.com>
 */
@Component(value = "prototype")
//@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeBean {

    private String name;

    public PrototypeBean() {
        System.out.println("this is constructor");
    }

    @PostConstruct
    public void postConstruct() {
        this.name = "yeah";
        System.out.println("this is post construct");
    }

    static {
        System.out.println("this is static block");
    }

    public void init() {
        System.out.println("this is init");
    }

    public void say() {
        System.out.println("hello " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("set name value =" + name);
        this.name = name;
    }
}
