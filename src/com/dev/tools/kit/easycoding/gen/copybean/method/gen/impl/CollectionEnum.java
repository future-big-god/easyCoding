package com.dev.tools.kit.easycoding.gen.copybean.method.gen.impl;

public enum CollectionEnum {

    LIST("list","ArrayList"),
    SET("set","HashSet");

    private String name;

    private String implName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImplName() {
        return implName;
    }

    public void setImplName(String implName) {
        this.implName = implName;
    }

    CollectionEnum(String name, String implName) {
        this.name = name;
        this.implName = implName;
    }
}
