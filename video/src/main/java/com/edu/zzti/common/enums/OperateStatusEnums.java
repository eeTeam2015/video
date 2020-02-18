package com.edu.zzti.common.enums;

/**
 * 全局状态
 */
public enum OperateStatusEnums {

    /**
     * 操作成功
     */
    SUCCESS(0,"成功"),

    /**
     * 操作失败
     */
    FAIL(-1,"失败");

    private Integer value;

    private String name;

    OperateStatusEnums(Integer value, String name) {
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

    public static OperateStatusEnums ofValue(Integer value){
        for (OperateStatusEnums operateStatusEnums : OperateStatusEnums.values()) {
            if(operateStatusEnums.getValue().equals(value)){
                return operateStatusEnums;
            }
        }
        return null;
    }
}
