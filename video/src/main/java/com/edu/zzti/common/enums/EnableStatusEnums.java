package com.edu.zzti.common.enums;


/**
 * 启用状态
 */
public enum  EnableStatusEnums {

    /**
     * 启用
     */
    ENABLE(1,"启用"),

    /**
     * 禁用
     */
    DISABLE(0,"禁用");

    private Integer value;

    private String name;

    EnableStatusEnums(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getStringValue() {
        return String.valueOf(value);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static EnableStatusEnums ofValue(Integer value){
        for (EnableStatusEnums enableStatusEnums : EnableStatusEnums.values()) {
            if(enableStatusEnums.getValue().equals(value)){
                return enableStatusEnums;
            }
        }
        return null;
    }
}
