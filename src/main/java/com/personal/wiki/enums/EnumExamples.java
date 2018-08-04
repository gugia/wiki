package com.personal.wiki.enums;

import com.google.gson.Gson;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 枚举用法示例
 *
 * @author L.X <gugia@qq.com>
 */
@Slf4j
public class EnumExamples {

    final static Gson GSON = new Gson();

    @Data
    static class Backet implements Serializable {

        private FruitEnum fruitEnum;
        private ColorEnum colorEnum;
        private Integer mount;
    }

    public static void main(String[] args) {
        log.info(FruitEnum.APPLE.name());
        log.info(FruitEnum.BANANA.toString());
        Backet backet = new Backet();
        backet.setFruitEnum(FruitEnum.ORANGE);
        backet.setColorEnum(ColorEnum.valueOf("RED"));
        backet.setMount(256);
        log.info(GSON.toJson(backet));
    }
}
