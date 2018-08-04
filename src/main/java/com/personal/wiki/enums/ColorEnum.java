package com.personal.wiki.enums;

import lombok.Getter;

/**
 * 颜色枚举
 *
 * @author L.X <gugia@qq.com>
 */
public enum ColorEnum {

    WHITE(0, "白色"),
    BLACK(1, "黑色"),
    RED(2, "红色"),
    YELLOW(3, "黄色"),
    BLUE(4, "蓝色");

    @Getter
    private final int code;
    @Getter
    private final String name;

    ColorEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
