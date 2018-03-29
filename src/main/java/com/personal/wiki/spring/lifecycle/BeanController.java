package com.personal.wiki.spring.lifecycle;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring - 容器的生命周期<br>
 * 访问/lifecycle/{name}每次都可观察到生成多例对象(prototype)的过程
 *
 * @author L.X <gugia@qq.com>
 */
@Controller
public class BeanController {

    @ResponseBody
    @RequestMapping("/lifecycle/{name}")
    public String index(@PathVariable("name") String name, PrototypeBean prototypeBean) {
        prototypeBean.setName(name);
        prototypeBean.say();
        return prototypeBean.toString();
    }
}
